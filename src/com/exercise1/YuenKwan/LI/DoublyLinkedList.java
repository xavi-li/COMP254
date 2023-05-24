/*
 * Copyright 2014, Michael T. Goodrich, Roberto Tamassia, Michael H. Goldwasser
 *
 * Developed for use with the book:
 *
 *    Data Structures and Algorithms in Java, Sixth Edition
 *    Michael T. Goodrich, Roberto Tamassia, and Michael H. Goldwasser
 *    John Wiley & Sons, 2014
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.exercise1.YuenKwan.LI;

/**
 * A basic doubly linked list implementation.
 *
 * @author Michael T. Goodrich
 * @author Roberto Tamassia
 * @author Michael H. Goldwasser
 */
public class DoublyLinkedList<E> {

  //---------------- nested Node class ----------------
  /**
   * Node of a doubly linked list, which stores a reference to its
   * element and to both the previous and next node in the list.
   */
  private static class Node<E> {

    /** The element stored at this node */
    private E element;               // reference to the element stored at this node

    /** A reference to the preceding node in the list */
    private Node<E> prev;            // reference to the previous node in the list

    /** A reference to the subsequent node in the list */
    private Node<E> next;            // reference to the subsequent node in the list

    /**
     * Creates a node with the given element and next node.
     *
     * @param e  the element to be stored
     * @param p  reference to a node that should precede the new node
     * @param n  reference to a node that should follow the new node
     */
    public Node(E e, Node<E> p, Node<E> n) {
      element = e;
      prev = p;
      next = n;
    }

    // public accessor methods
    /**
     * Returns the element stored at the node.
     * @return the element stored at the node
     */
    public E getElement() { return element; }

    /**
     * Returns the node that precedes this one (or null if no such node).
     * @return the preceding node
     */
    public Node<E> getPrev() { return prev; }

    /**
     * Returns the node that follows this one (or null if no such node).
     * @return the following node
     */
    public Node<E> getNext() { return next; }

    // Update methods
    /**
     * Sets the node's previous reference to point to Node n.
     * @param p    the node that should precede this one
     */
    public void setPrev(Node<E> p) { prev = p; }

    /**
     * Sets the node's next reference to point to Node n.
     * @param n    the node that should follow this one
     */
    public void setNext(Node<E> n) { next = n; }

  } //----------- end of nested Node class -----------

  // instance variables of the DoublyLinkedList
  /** Sentinel node at the beginning of the list */
  private Node<E> header;                    // header sentinel

  /** Sentinel node at the end of the list */
  private Node<E> trailer;                   // trailer sentinel

  /** Number of elements in the list (not including sentinels) */
  private int size = 0;                      // number of elements in the list

  /** Constructs a new empty list. */
  public DoublyLinkedList() {
    header = new Node<>(null, null, null);      // create header
    trailer = new Node<>(null, header, null);   // trailer is preceded by header
    header.setNext(trailer);                    // header is followed by trailer
  }

  // public accessor methods
  /**
   * Returns the number of elements in the linked list.
   * @return number of elements in the linked list
   */
  public int size() { return size; }

  /**
   * Tests whether the linked list is empty.
   * @return true if the linked list is empty, false otherwise
   */
  public boolean isEmpty() { return size == 0; }

  /**
   * Returns (but does not remove) the first element of the list.
   * @return element at the front of the list (or null if empty)
   */
  public E first() {
    if (isEmpty()) return null;
    return header.getNext().getElement();   // first element is beyond header
  }

  /**
   * Returns (but does not remove) the last element of the list.
   * @return element at the end of the list (or null if empty)
   */
  public E last() {
    if (isEmpty()) return null;
    return trailer.getPrev().getElement();    // last element is before trailer
  }

  // public update methods
  /**
   * Adds an element to the front of the list.
   * @param e   the new element to add
   */
  public void addFirst(E e) {
    addBetween(e, header, header.getNext());    // place just after the header
  }

  /**
   * Adds an element to the end of the list.
   * @param e   the new element to add
   */
  public void addLast(E e) {
    addBetween(e, trailer.getPrev(), trailer);  // place just before the trailer
  }

  /**
   * Removes and returns the first element of the list.
   * @return the removed element (or null if empty)
   */
  public E removeFirst() {
    if (isEmpty()) return null;                  // nothing to remove
    return remove(header.getNext());             // first element is beyond header
  }

  /**
   * Removes and returns the last element of the list.
   * @return the removed element (or null if empty)
   */
  public E removeLast() {
    if (isEmpty()) return null;                  // nothing to remove
    return remove(trailer.getPrev());            // last element is before trailer
  }

  // private update methods
  /**
   * Adds an element to the linked list in between the given nodes.
   * The given predecessor and successor should be neighboring each
   * other prior to the call.
   *
   * @param predecessor   node just before the location where the new element is inserted
   * @param successor     node just after the location where the new element is inserted
   */
  private void addBetween(E e, Node<E> predecessor, Node<E> successor) {
    // create and link a new node
    Node<E> newest = new Node<>(e, predecessor, successor);
    predecessor.setNext(newest);
    successor.setPrev(newest);
    size++;
  }

  /**
   * Removes the given node from the list and returns its element.
   * @param node    the node to be removed (must not be a sentinel)
   */
  private E remove(Node<E> node) {
    Node<E> predecessor = node.getPrev();
    Node<E> successor = node.getNext();
    predecessor.setNext(successor);
    successor.setPrev(predecessor);
    size--;
    return node.getElement();
  }

  /**
   * Produces a string representation of the contents of the list.
   * This exists for debugging purposes only.
   */
  public String toString() {
    StringBuilder sb = new StringBuilder("(");
    Node<E> walk = header.getNext();
    while (walk != trailer) {
      sb.append(walk.getElement());
      walk = walk.getNext();
      if (walk != trailer)
        sb.append(", ");
    }
    sb.append(")");
    return sb.toString();
  }

  /**
   * Swaps two nodes in the linked list given references to node1 and node2.
   *
   * @param node1 the first node to swap
   * @param node2 the second node to swap
   */
  public void swapTwoNodes(Node<E> node1, Node<E> node2) {
    // Return if either node is null or if they are the same node
    if (node1 == null || node2 == null || node1 == node2) {
      return;
    }

    // Check if the nodes are adjacent
    if (node1.getNext() == node2 || node2.getNext() == node1) {
      // Swap the previous references
      Node<E> tempPrev1 = node1.getPrev();
      node1.setPrev(node2);
      node2.setPrev(tempPrev1);

      // Swap the next references
      Node<E> tempNext2 = node2.getNext();
      node2.setNext(node1);
      node1.setNext(tempNext2);

      // Update previous reference of node1's next node
      if (node1.getNext() != null) {
        node1.getNext().setPrev(node1);
      } else {
        trailer.setPrev(node1);
      }

      // Update next reference of node2's previous node
      if (node2.getPrev() != null) {
        node2.getPrev().setNext(node2);
      } else {
        header.setNext(node2);
      }
    } else {
      // Swap non-adjacent nodes
      // get the previous and next nodes of node1 and node2
      Node<E> prevNode1 = node1.getPrev();
      Node<E> nextNode1 = node1.getNext();
      Node<E> prevNode2 = node2.getPrev();
      Node<E> nextNode2 = node2.getNext();

      // set the previous and next references for node1 and node2
      node1.setPrev(prevNode2);
      node1.setNext(nextNode2);
      node2.setPrev(prevNode1);
      node2.setNext(nextNode1);

      // update next reference of node1's previous node
      if (prevNode1 != null) {
        prevNode1.setNext(node2);
      } else {
        header.setNext(node2);
      }

      // Update previous reference of node1's next node
      if (nextNode1 != null) {
        nextNode1.setPrev(node2);
      } else {
        trailer.setPrev(node2);
      }

      // Update next reference of node2's previous node
      if (prevNode2 != null) {
        prevNode2.setNext(node1);
      } else {
        header.setNext(node1);
      }

      // Update previous reference of node2's next node
      if (nextNode2 != null) {
        nextNode2.setPrev(node1);
      } else {
        trailer.setPrev(node1);
      }
    }
  }

  /* YuenKwanLI (Xavier) 301228849 */
//main method
  public static void main(String[] args)
  {
	  // Create and populate a doubly linked list
	  DoublyLinkedList<String> list = new DoublyLinkedList<String>();
	  list.addFirst("MSP");
	  list.addLast("ATL");
	  list.addLast("BOS");
	  list.addFirst("LAX");
	  //

      // Test #1 - 1st and 2nd nodes
      // Get references to the 1st and 2nd nodes
      Node<String> node1 = list.header.getNext(); // 1st node
      Node<String> node2 = list.header.getNext().getNext(); // 2th node

      // Display the original list
      System.out.println("Test #1 - Original List: " + list);

      // Display the nodes to swap
      System.out.println("Test #1 - Nodes to swap: " + node1.getElement() + ", " + node2.getElement());

      // Swap the nodes
      list.swapTwoNodes(node1, node2);

      // Display the swapped list
      System.out.println("Test #1 - List after swap: " + list + "\n");

      // Test #2 - 1st and 3rd nodes
      // Get references to the 1st and 3rd nodes
      node1 = list.header.getNext(); // 1st node
      node2 = list.header.getNext().getNext().getNext(); // 3rd node

      // Display the original list
      System.out.println("Test #2 - Original List: " + list);

      // Display the nodes to swap
      System.out.println("Test #2 - Nodes to swap: " + node1.getElement() + ", " + node2.getElement());

      // Swap the nodes
      list.swapTwoNodes(node1, node2);

      // Display the swapped list
      System.out.println("Test #2 - List after swap: " + list + "\n");

      // Test #3 - 1st and 4th nodes
      // Get references to the 1st and 4th nodes
      node1 = list.header.getNext(); // 1st node
      node2 = list.trailer.getPrev(); // 4th node

      // Display the original list
      System.out.println("Test #3 - Original List: " + list);

      // Display the nodes to swap
      System.out.println("Test #3 - Nodes to swap: " + node1.getElement() + ", " + node2.getElement());

      // Swap the nodes
      list.swapTwoNodes(node1, node2);

      // Display the swapped list
      System.out.println("Test #3 - List after swap: " + list + "\n");

      // Test #4 - 2nd and 3rd nodes
      // Get references to the 2nd and 3rd nodes
      node1 = list.header.getNext().getNext(); // 2nd node
      node2 = list.header.getNext().getNext().getNext(); // 3rd node

      // Display the original list
      System.out.println("Test #4 - Original List: " + list);

      // Display the nodes to swap
      System.out.println("Test #4 - Nodes to swap: " + node1.getElement() + ", " + node2.getElement());

      // Swap the nodes
      list.swapTwoNodes(node1, node2);

      // Display the swapped list
      System.out.println("Test #4 - List after swap: " + list + "\n");

      // Test #5 - 2nd and 4th nodes
      // Get references to the 2nd and 4th nodes
      node1 = list.header.getNext().getNext(); // 2nd node
      node2 = list.trailer.getPrev(); // 4th node

      // Display the original list
      System.out.println("Test #5 - Original List: " + list);

      // Display the nodes to swap
      System.out.println("Test #5 - Nodes to swap: " + node1.getElement() + ", " + node2.getElement());

      // Swap the nodes
      list.swapTwoNodes(node1, node2);

      // Display the swapped list
      System.out.println("Test #5 - List after swap: " + list + "\n");

      // Test #6 - 3rd and 4th nodes
      // Get references to the 3rd and 4th nodes
      node1 = list.header.getNext().getNext().getNext(); // 3rd node
      node2 = list.trailer.getPrev(); // 4th node

      // Display the original list
      System.out.println("Test #6 - Original List: " + list);

      // Display the nodes to swap
      System.out.println("Test #6 - Nodes to swap: " + node1.getElement() + ", " + node2.getElement());

      // Swap the nodes
      list.swapTwoNodes(node1, node2);

      // Display the swapped list
      System.out.println("Test #6 - List after swap: " + list + "\n");

  }
} //----------- end of DoublyLinkedList class -----------
