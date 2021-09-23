package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
		sc.nextLine();
		System.out.print("내용 >> ");
		desc = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
		System.out.println("추가되었습니다.");
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

		System.out.print("새 제목 >> ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("제목은 중복될 수 없습니다.");
			return;
		}
		sc.nextLine();
		System.out.print("새 내용 >> ");
		String new_description = sc.nextLine().trim();
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
	
	public static void saveList(TodoList l, String filename) {
		FileWriter w = null;
		try {
			w = new FileWriter(filename);
			for(TodoItem itemToWrite : l.getList()) {
				w.write(itemToWrite.toSaveString());
			}
			System.out.println("데이터가 저장되었습니다.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				w.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void loadList(TodoList l, String filename) {
		BufferedReader r;
		int count = 0;
		try {
			r = new BufferedReader(new FileReader(filename));
			String data = null;
			try {
				while((data = r.readLine())!=null) {
					StringTokenizer stk = new StringTokenizer(data, "##");
					l.addItem(new TodoItem(stk.nextToken(),stk.nextToken(),stk.nextToken()));
					count++;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(count + "개의 항목을 읽었습니다.");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(filename + " 파일이 없습니다.");
		}
	
	}
}
