import java.util.LinkedList;
import java.util.Queue;

public class QueueDemo {

  public void demonstrateQueue() {
    // Queue = FIFO (First In First Out) data structure
    // e.g ., line of people waiting
    //
    // can't instantiate Queue directly bcz it's an interface
    //    Queue<String> queue = new Queue<String>();

    // can use LinkedList which implements Queue interface
    Queue<String> queue = new LinkedList<>();

    queue.offer("Person A");
    queue.offer("Person B");
    queue.offer("Person C");
    queue.offer("Person D");

//    System.out.println(queue.peek());

    queue.poll();
    queue.poll();
    queue.poll();
    queue.poll();
    queue.poll(); // will not throw an error
//    queue.element(); // throws an exception if the queue is empty

    System.out.println(queue);
  }
}
