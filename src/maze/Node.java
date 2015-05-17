package maze;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
//import java.util.function.Predicate;

/**
 * @author Hermann Tom
 * @date 2015/05/05
 */

public class Node {

	private Coordinate2D coord;
	private List<Node> linkedNeighbours;
	private List<Node> neighbours;
	private boolean linked;

	public Node(Coordinate2D coord) {
		Objects.requireNonNull(coord);
		this.coord = coord;
		linkedNeighbours = new ArrayList<>(8);
	}

	public boolean isLinked() {
		return linked;
	}

	public void setLinked(boolean linked) {
		this.linked = linked;
	}

	public Node linkRandomUnlinkedNeighbour() {
		List<Node> list = new ArrayList<>(getNeighbours());

//		list.removeIf( new Predicate<Node>() {
//			@Override
//			public boolean test(Node n) {
//				return n.linked;
//			}
//		});
		if (list.isEmpty())
			return null;
		Collections.shuffle(list);
		Node next = list.get(0);
		next.getNeighbours();
		linkedNeighbours.add(next);
		next.linkedNeighbours.add(this);
		next.linked = true;
		return next;
	}

	public List<Node> getNeighbours() {
		if (neighbours == null) {
			List<Node> nodes = new ArrayList<>(Arrays.asList(minusX(), plusX(),
					minusY(), plusY()));

//			nodes.removeIf(new Predicate<Node>() {
//				@Override
//				public boolean test(Node x) {
//					return x == null;
//				}
//			});
			 
			neighbours = Collections.unmodifiableList(nodes);
		}
		return neighbours;
	}

	public boolean isDeadEnd() {
		return linkedNeighbours.size() == 1;
	}

	public boolean isBranch() {
		return linkedNeighbours.size() > 2;
	}

	public boolean isLinkedTo(Node node) {
		return linkedNeighbours.contains(node);
	}

	public Maze getMaze() {
		return coord.getMaze();
	}

	public Coordinate2D getCoord() {
		return coord;
	}

	public Node minusX() {
		return getMaze().nodeAt(coord.minusX());
	};

	public Node plusX() {
		return getMaze().nodeAt(coord.plusX());
	};

	public Node minusY() {
		return getMaze().nodeAt(coord.minusY());
	};

	public Node plusY() {
		return getMaze().nodeAt(coord.plusY());
	};
}
