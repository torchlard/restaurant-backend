package restaurant.config

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.AfterThrowing
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.LoggerFactory
import org.springframework.core.env.Environment
import org.springframework.core.env.Profiles
import java.lang.IllegalArgumentException


@Suppress("unused")
@Aspect
open class LoggingAspect(private val env: Environment){

  private val log = LoggerFactory.getLogger(this.javaClass)

  @Pointcut("within(@org.springframework.stereotype.Repository *)"+
    " || within(@org.springframework.stereotype.Service *)"+
    " || within(@org.springframework.web.bind.annotation.RestController *)")
  fun springBeanPointcut() = Unit

  @Pointcut("within(restaurant.repository..*)"+
     " || within(restaurant.service..*)"+
     " || within(restaurant.controller..*)")
  fun applicationPackagePointcut() = Unit

  //  advice that log methods throwing exception
  @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
  fun logAfterThrowing(joinPoint: JoinPoint, e: Throwable){
    log.error("Exception in {}, {} () with cause = \'{}\' and exception = \'{}\'",
       joinPoint.signature.declaringTypeName, joinPoint.signature.name,
        if(e.cause != null) e.cause else "NULL", e.message, e
      )
  }

//  advice that log when method is entered and exited
  @Around(value = "applicationPackagePointcut() && springBeanPointcut()")
  fun logAround(joinPoint: ProceedingJoinPoint): Any? {
    val startTime = System.currentTimeMillis()
    if(log.isDebugEnabled){
      log.debug("Enter: {},{}() with argument[s] = {}", joinPoint.signature.declaringTypeName,
        joinPoint.signature.name, joinPoint.args.joinToString()  )
    }
    try {
      val result = joinPoint.proceed()
      if(log.isDebugEnabled){
        val endTime = System.currentTimeMillis()
        val duration = endTime - startTime
        log.debug("Exit: {},{}() with result = {} ({}ms)", joinPoint.signature.declaringTypeName,
           joinPoint.signature.name, result, duration )
      }
      return result
    } catch (e: IllegalArgumentException){
      val duration = System.currentTimeMillis() - startTime
      log.error("Illegal argument: {} in {},{}() ({}ms)", joinPoint.args.joinToString(),
         joinPoint.signature.declaringTypeName, joinPoint.signature.name, duration )
      throw e
    }
  }

//  @Around("@annotation(LoggingInProduction)")
//  fun logInProduction(joinPoint: ProceedingJoinPoint): Any? {
//    val startTime = System.currentTimeMillis()
//    if(log.isDebugEnabled){
//      log.debug("Enter: {},{}() with argument[s] = {}", joinPoint.signature.declaringTypeName,
//        joinPoint.signature.name, joinPoint.args.joinToString()  )
//    }
//    try {
//      val result = joinPoint.proceed()
//      if(log.isDebugEnabled){
//        val endTime = System.currentTimeMillis()
//        val duration = endTime - startTime
//        log.debug("Exit: {},{}() with result = {} ({}ms)", joinPoint.signature.declaringTypeName,
//          joinPoint.signature.name, result, duration )
//      }
//      return result
//    } catch (e: IllegalArgumentException){
//      val duration = System.currentTimeMillis() - startTime
//      log.error("Illegal argument: {} in {},{}() ({}ms)", joinPoint.args.joinToString(),
//        joinPoint.signature.declaringTypeName, joinPoint.signature.name, duration )
//      throw e
//    }
//  }



}





