package com.todo.service;

import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("[항목 추가]");
		System.out.print("제목 >> ");
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("중복된 제목은 사용할 수 없습니다.");
			return;
		}
		
		System.out.print("내용 >> ");
		desc = sc.next();
		
		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("[항목 삭제]");
		System.out.print("삭제할 항목의 제목을 입력하시오 >> ");
		String title = sc.next();
		
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				System.out.println("삭제되었습니다.");
				break;
			}
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("[항목 수정]");
		System.out.print("수정할 항목의 제목을 입력하시오 >> ");
		
		String title = sc.next().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("존재하지 않는 제목입니다.");
			return;
		}

		System.out.print("새제목 >> ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("제목은 중복될 수 없습니다.");
			return;
		}
		
		System.out.print("새 내용 >> ");
		String new_description = sc.next().trim();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("수정되었습니다.");
			}
		}

	}

	public static void listAll(TodoList l) {
		System.out.println("[전체 할일 목록]");
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}
}
