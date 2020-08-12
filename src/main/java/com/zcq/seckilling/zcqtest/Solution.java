package com.zcq.seckilling.zcqtest;

/**
 * @Description: 数组的查询
 * @Author: zcq
 * @Date: 2018/10/30 6:35 PM
 */
public class Solution {

	private Trie trie = new Trie();

	private void addWord(String s) {
		trie.insert(s);
	}

	public boolean search(String s) {
		return trie.search(s);
	}

	private class Trie {

		private class Node {
			Node[] childs = new Node[26];
			boolean isLeaf;
		}

		private Node root = new Node();

		void insert(String word) {
			insert(word, root);
		}

		private void insert(String word, Node node) {
			if (node == null) return;
			if (word.length() == 0) {
				node.isLeaf = true;
				return;
			}
			int index = indexForChar(word.charAt(0));
			if (node.childs[index] == null) {
				node.childs[index] = new Node();
			}
			insert(word.substring(1), node.childs[index]);
		}

		boolean search(String word) {
			return search(word, root);
		}

		private boolean search(String word, Node node) {
			if (node == null) return false;
			if (word.length() == 0) return node.isLeaf;
			char c = word.charAt(0);
			String next = word.substring(1);
			if (c == '.') {
				for (Node child : node.childs) {
					if (search(next, child)) return true;
				}
				return false;
			} else {
				int index = indexForChar(c);
				return search(next, node.childs[index]);
			}
		}

		private int indexForChar(char c) {
			return c - 'a';
		}
	}

	public static void main(String[] args) {
		Solution solution = new Solution();
		solution.addWord("bad");
		solution.addWord("dad");
		solution.addWord("mad");
		System.out.println(solution.search("pad"));
		System.out.println(solution.search("bad"));
		System.out.println(solution.search(".ad"));
		System.out.println(solution.search("..d"));
	}
}
