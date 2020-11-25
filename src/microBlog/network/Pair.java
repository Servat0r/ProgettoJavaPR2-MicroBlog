package microBlog.network;

/**
 * Semplice utility per rappresentare una coppia di interi ed oggetti di tipo generico, utilizzata nella classe
 * MicroBlog.
 * 
 * @author Salvatore Correnti
 * 
 * @see MicroBlog
 *
 */
final class Pair<T> implements Comparable<T> {

	private int first;
	private final T second;
	
	public Pair(int first, T second) {
		this.first = first;
		this.second = second;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + first;
		result = prime * result + ((second == null) ? 0 : second.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pair<T> other = (Pair<T>) obj;
		if (first != other.first)
			return false;
		if (second == null) {
			if (other.second != null)
				return false;
		} else if (!second.equals(other.second))
			return false;
		return true;
	}

	/**
	 * @return the first
	 */
	final int getFirst() {
		return first;
	}

	/**
	 * @param first the first to set
	 */
	final void setFirst(int first) {
		this.first = first;
	}

	/**
	 * @return the second
	 */
	final T getSecond() {
		return second;
	}

	public int compareTo(Object o) {
		try {
			Pair<T> op = (Pair<T>)o;
			return this.getFirst() - op.getFirst(); //In ordine decrescente
		} catch (ClassCastException e) {
			throw new IllegalArgumentException("Must pass a Pair!");
		}
	}

	public String toString() {
		return "Pair [first=" + first + ", second=" + second + "]";
	}
	
	protected Pair<T> copy() {
		return new Pair<T>(this.getFirst(), this.getSecond());
	}
}