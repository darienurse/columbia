import x10.util.ArrayList;

public class WebNode {
	val id: int;
    val links: ArrayList[WebNode];
    def this(id:Int, links:ArrayList[WebNode]) {
        this.id = id;
        this.links = links;
    }
    def toString() {
    	var result:String = ": ";
    	for(var i:Int = 0n; i<links.size(); i++) {
    		result += links(i).id+" ";
    	}
    	return result;
    }
}
