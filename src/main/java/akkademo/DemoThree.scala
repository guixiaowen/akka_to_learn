package akkademo

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props}

object DemoThree extends App {

  class FirstActor extends Actor with ActorLogging {

    var child: ActorRef = _

    override def preStart(): Unit = {
      log.info("preStart() in FirstActor")

      child = context.actorOf(Props[MyActor], name = "myChild")
    }

    override def receive: Receive = {
      case x => child ! x;
        log.info("received : " + x)
    }

    override def postStop(): Unit = {
      log.info("postStop() in FirstActor")
    }
  }

  class MyActor extends Actor with ActorLogging {

    override def preStart(): Unit = {
      log.info("preStart() in MyActor")
    }

    override def receive: Receive = {
      case "test" => log.info("received test")
      case _ => log.info("received unknown ")
    }

    override def postStop(): Unit = {
      log.info("postStop() in MyActor")
    }
  }


  val system = ActorSystem("MyActorSystem")
  val systemLog = system.log

  //创建FirstActor对象
  val myactor = system.actorOf(Props[FirstActor], name = "firstActor")

  systemLog.info("准备向myactor发送消息")
  //向myactor发送消息
  myactor!"test"
  myactor! 123
  Thread.sleep(5000)
  //关闭ActorSystem，停止程序的运行
//  system.shutdown()

}
