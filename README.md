The project implements an intra-procedural transform that iterates over the set of all Jimple statements and prints out those that read and write heap locations. Each Java method in soot is represented by a SootMethod object. From this object, we retrieve the body of the method by calling getActiveBody(). This will give us a Body object, through which we have access to all information on the Java method, such as local variables and statements.
We can then use the method getUnits() to get a chain of statements the method contains. Write a while loop that iterates over the statements in the chain. In Jimple, each statement is an object of interface Stmt, which has many different implementing classes. Among these Stmt classes, we are interested in JAssignStmt, which represents assignment statements (e.g., anything of the form a = b). To identify statements that access the heap, we need to understand what the left-hand-side (LHS) and RHS operands are for each statement. To do this, we can call methods getLeftOp() and getRightOp() on each statement object. These calls return objects of type Value, which is an interface representing all expressions, locals, constants, and references in a Java program.
We are particularly interested in InstanceFieldRef, StaticFieldRef, and ArrayRef, each of which represents a type of heap access.
If the LHS/RHS of an assignment is an InstanceFieldRef, the assignment is of the form:
a.f = b(heap write)
b = a.f (heap read);
If the LHS/RHS of an assignment is a StaticFieldRef, the assignment is of the form:
A.f = b (heap write)
b=A.f (heap read);
If the LHS/RHS of an assignment is an ArrayRef, the assignment is of the form:
a[i] = b (heap write)
b=a[i] (heap read);
The output of the analysis should have the following format:
Statement a.f = b, heap write;
Statement b = a[i], heap read;
