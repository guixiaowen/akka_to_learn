package akkademo
import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props}

object DemoFive extends App{


  class FirstActor extends Actor with ActorLogging{
    //通过context.actorOf方法创建Actor
    var child:ActorRef = _
    override def preStart(): Unit ={
      log.info("preStart() in FirstActor")
      //通过context上下文创建Actor
      child = context.actorOf(Props[MyActor], name = "myActor")
    }

    def receive = {
      //向MyActor发送消息
      case x => child ! x;log.info("received "+x)
    }
  }

  class MyActor extends Actor with ActorLogging{
    def receive = {
      case "test" => log.info("received test");
      case _      => log.info("received unknown message");
    }
  }

  val system = ActorSystem("MyActorSystem")
  val systemLog = system.log

  val firstactor = system.actorOf(Props[FirstActor], name = "firstActor")
  //获取ActorPath
  val firstActorPath=system.child("firstActor")
  systemLog.info("firstActorPath--->{}",firstActorPath)


}
