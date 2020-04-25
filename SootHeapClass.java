import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import soot.*;
import soot.jimple.internal.JAssignStmt;
import soot.jimple.AssignStmt;
import soot.jimple.DefinitionStmt;
import soot.jimple.FieldRef;
import soot.jimple.IdentityStmt;
import soot.jimple.InstanceFieldRef;
import soot.jimple.InstanceInvokeExpr;
import soot.jimple.IntConstant;
import soot.jimple.InvokeExpr;
import soot.jimple.InvokeStmt;
import soot.jimple.Jimple;
import soot.jimple.NullConstant;
import soot.jimple.StaticFieldRef;
import soot.jimple.Stmt;
import soot.jimple.StringConstant;
import soot.options.Options;
import soot.jimple.GotoStmt;
import soot.jimple.ArrayRef;

public class SootHeapClass extends BodyTransformer {


public static void main(String[] args)  {

String javapath = System.getProperty("java.class.path");
String jredir = System.getProperty("java.home")+"/lib/rt.jar";
String path = javapath+File.pathSeparator+jredir;
Scene.v().setSootClassPath(path);

SootHeapClass analysis = new SootHeapClass();
PackManager.v().getPack("jtp").add(new Transform("jtp.SootHeapClass", analysis));

Options.v().set_app(true);
SootClass appclass = Scene.v().loadClassAndSupport("fibonacci");
Scene.v().setMainClass(appclass);

Scene.v().loadNecessaryClasses();

PackManager.v().runPacks();

PackManager.v().writeOutput();
}

@Override
protected void internalTransform(Body b, String phaseName,
Map<String, String> options) {
Iterator<Unit> it = b.getUnits().snapshotIterator();
while(it.hasNext()){
Unit u = it.next();
if(u instanceof Stmt){
Stmt gtSt = (Stmt) u;
if(gtSt instanceof AssignStmt){
AssignStmt js = (AssignStmt)gtSt;
Value lhs = js.getLeftOp();
Value rhs = js.getRightOp();
if(lhs instanceof ArrayRef){
G.v().out.println("Statement " +lhs.toString() + " = " + rhs.toString() + ",\t\t\t heap write");
}
if(rhs instanceof ArrayRef){
G.v().out.println("Statement " + lhs.toString() + " = " + rhs.toString() + ",\t\t\t heap read");
}
if(lhs instanceof InstanceFieldRef){
G.v().out.println("Statement " +lhs.toString() + " = " + rhs.toString() + ",\t\t heap write");
}
if(rhs instanceof InstanceFieldRef){
G.v().out.println("Statement " +lhs.toString() + " = " + rhs.toString() + ",\t\t heap read");
}
if(lhs instanceof StaticFieldRef){
G.v().out.println("Statement " +lhs.toString() + " = " + rhs.toString() + ",\t\t\t heap write");
}
if(rhs instanceof StaticFieldRef){
G.v().out.println("Statement " +lhs.toString() + " = " + rhs.toString() + ",\t heap read");
}
}
}
}
}
}