import x10.util.Timer;
import x10.util.ArrayList;
import x10.util.List;
import x10.util.HashSet;
import x10.util.Set;

/**
 * This is the class that provides the solve() method.
 *
 * The assignment is to replace the contents of the solve() method
 * below with code that actually works :-)
 */
public class Solver
{
    /**
     * Solve a single 'N'-Queens with pawns problem.
     *     'size' is 'N'.
     *     'pawns' is an array of bidimensional Point {rank==2} with the locations of pawns.  The array may be of length zero.
     *
     * This function should return the number of solutions for the given configuration.
     */
    public def solve(size: int, pawns: ArrayList[Point{rank==2}]) : int
    {
    	if (!pawns.isEmpty())
    	{
	    	val spaces = new HashSet[Point{rank==2}](((size * size) - pawns.size()) as int);
	    	for (i in 0n..(size-1))
	    	{
	    		for (j in 0n..(size-1))
	    		{
	    			var blocked: boolean = false;
	    			for (pawn in pawns)
	    			{
	    				if (i == pawn(0) && j == pawn(1))
	    				{
	    					blocked = true;
	    					break;
	    				}
	    			}
	    			
	    			if (!blocked)
	    			{
	    				spaces.add(Point.make(i, j));
	    			}
	    		}
	    	}
	    	//Console.OUT.println("\n\n\ntesting board of size " + size + " with pawns: " + pawns.toString());
	    	
	    	return solve(size, 0n, pawns, spaces);
    	}
    	else
    	{
    		return solve(size, 0n, new ArrayList[Point{rank==2}](size));
    	}
    	
        // Your solution goes here
    }
    
    private def solve(size: int, queens: int, pawns: ArrayList[Point{rank==2}], spaces: HashSet[Point{rank==2}]) : int
    {
    	if (queens == size-1n) {
    		return spaces.size() as int;
    	}
    	
    	var result: int = 0n;
    	val usedSpaces = new HashSet[Point{rank==2}]();
    	for (queenSpace in spaces)
    	{
    		usedSpaces.add(queenSpace);
    		val subspace = new HashSet[Point{rank==2}]();
    		subspace.addAllWhere(spaces, (space: Point{rank==2}) => {return !usedSpaces.contains(space);});
    		
			subspace.removeAllWhere((space: Point{rank==2}) => {return pointsAttacking(queenSpace(0), space(0), queenSpace(1), space(1), pawns);});
			
			if (subspace.size() >= (size-queens-1)) // if there are no more spaces, this branch failed, do nothing
			{
				result += solve(size, queens+1n, pawns, subspace);
			}
    	}
    	return result;
    }
    
    private def solve(size: int, column: int, queens: ArrayList[Point{rank==2}]) : int
    {
    	var result: int = 0n;
    	for (row in 0..(size-1))
    	{
    		var blocked: boolean = false;
    		for (queen in queens)
    		{
    			// TODO: get rid of arraylist creation here
    			if (pointsAttacking(column as long, queen(0), row, queen(1), new ArrayList[Point{rank==2}](0)))
    			{
    				blocked = true;
    				break;
    			}
    		}
    		
    		if (!blocked) // if space is not blocked, place queen and obtain value from next column
    		{
    			if (column == (size - 1n))
    			{
    				//Console.OUT.println("valid config with queens: " + queens + " pawns: " + pawns + " at point: " + column + "," + row);
    				result++; // if at last column, add 1 to result for a free space
    			}
    			else
    			{
    				// add new queen in free space and call solve for next column
    				queens.add(Point.make(column, row));
    				result += solve(size, column + 1n, queens);
    				queens.removeLast();
    			}
    		}
    	}
    	return result;
    }
    
    private def pointsAttacking (x1:long, x2:long, y1:long, y2:long, pawns:ArrayList[Point{rank==2}]){
    	if (x1 == x2){
    		for(pawn in pawns){
    			if((x1 == pawn(0)) && ((pawn(1)<y1 && pawn(1)>y2) || (pawn(1)>y1 && pawn(1)<y2))){
    				return false;
    			}
    		}
    		//Console.OUT.println("failed for attack between " + x1 + "," + y1 + " and " + x2 + "," + y2);
    		return true;
    	}
		else if(y1 == y2){
			for (pawn in pawns){
				if((y1 == pawn(1)) && ((pawn(0)<x1 && pawn(0)>x2) || (pawn(0)>x1 && pawn(0)<x2))){
					return false;
				}
			}
			//Console.OUT.println("failed for attack between " + x1 + "," + y1 + " and " + x2 + "," + y2);
			return true;
		}
    	else if (Math.abs(x2-x1) == Math.abs(y2-y1)){
    		for(pawn in pawns){
    			if(Math.abs(x1-pawn(0)) == Math.abs(y1-pawn(1)) && Math.abs(x2-pawn(0)) == Math.abs(y2-pawn(1))){
    				if((pawn(0)<x1 && pawn(0)>x2) || (pawn(0)>x1 && pawn(0)<x2)){
    					return false;
    				}
    			}
    		}
    		//Console.OUT.println("failed for attack between " + x1 + "," + y1 + " and " + x2 + "," + y2);
    		return true;
    	}
    	else{
    		return false;
    	}
    }
}

