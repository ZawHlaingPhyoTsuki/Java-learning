import java.util.Stack;

public class StackDemo {


  // stack = LIFO (Last In First Out) data structure

  // uses of stacks
  // 1. undo/redo features in text editors
  // 2. backtracking algorithms (e.g., maze solving, file dir)
  // 3. moving back/forward in web browsers
  // 4. calling function (function call stack)

  public void demostrateStack() {
    Stack<String> stack = new Stack<>();
//    System.out.println(stack.isEmpty());

    stack.push("Minecraft");
    stack.push("Fortnite");
    stack.push("Roblox");
    stack.push("Valorant");
    stack.push("Among Us");

//    stack.pop();
//    stack.pop();
//    stack.pop();
//    stack.pop();
//    stack.pop();
//    stack.pop();

//    String myFavGame = stack.pop();
//    System.out.println(myFavGame);

//    System.out.println(stack.peek());
//    System.out.println(stack.search("Among Us")); // 1
//    System.out.println(stack.search("Some game")); // -1
    System.out.println(stack);
  }
}
