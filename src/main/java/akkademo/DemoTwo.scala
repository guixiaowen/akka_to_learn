package akkademo

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}

object DemoTwo extends App {

  class FirstActor extends Actor with ActorLogging {

    // 创建: myActor
    val child = context.actorOf(Props[MyActor], name = "myChild")

    override def receive: Receive = {
      // 发送给子类
      case x => child ! x;
        log.info("received : " + x)
    }
  }

  class MyActor extends Actor with ActorLogging {

    override def receive: Receive = {
      case "test" => log.info("received test")
      case _ => log.info("received unknown message")
    }
  }

  val system = ActorSystem("MyActorSystem")
  val systemLog=system.log

  //创建FirstActor对象
  val myactor = system.actorOf(Props[FirstActor], name = "firstActor")

  systemLog.info("准备向myactor发送消息")

  //向myactor发送消息
  myactor!"test"
  myactor! 123
  Thread.sleep(5000)

  //关闭ActorSystem，停止程序的运行
  //system.shutdown()

}
