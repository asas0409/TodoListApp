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
		
		String category, title, desc, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("[항목 추가]");
		
		
		System.out.print("제목 >> ");
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("중복된 제목은 사용할 수 없습니다.");
			return;
		}
		
		System.out.print("카테고리 >> ");
		category = sc.next();
		
		sc.nextLine();
		System.out.print("내용 >> ");
		desc = sc.nextLine().trim();
		
		System.out.print("마감일자 >> ");
		due_date = sc.next();
		
		TodoItem t = new TodoItem(title, category, desc, due_date);
		list.addItem(t);
		System.out.println("추가되었습니다.");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);

		System.out.println("[항목 삭제]");
		System.out.print("삭제할 항목의 번호를 입력하시오 >> ");
		int num = 1;
		int del_num = sc.nextInt();
		String del;
		
		for (TodoItem item : l.getList()) {
			if (del_num == num) {
				System.out.println(del_num + ". " +item.toString());
				System.out.print("위 항목을 삭제하시겠습니까? (y/n) > ");
				del = sc.next();
				if(del.equals("y")) {
					l.deleteItem(item);
					System.out.println("삭제되었습니다.");
				}
				else {
					System.out.println("취소되었습니다.");
				}
				break;
			}
			num++;
		}
			
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("[항목 수정]");
		System.out.print("수정할 항목의 번호를 입력하시오 >> ");
		int num = 1;
		int update_num = sc.nextInt();
		
		for (TodoItem item : l.getList()) {
			if (update_num == num) {
				System.out.println(update_num + ". " +item.toString());
				l.deleteItem(item);
				break;
			}
			num++;
		}
		
		System.out.print("새 제목 >> ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("제목은 중복될 수 없습니다.");
			return;
		}
		
		System.out.print("새 카테고리 >> ");
		String new_category = sc.next().trim();
		
		sc.nextLine();
		System.out.print("새 내용 >> ");
		String new_description = sc.nextLine().trim();
		
		System.out.print("새 마감일자 >> ");
		String new_due_date = sc.next().trim();
		
		TodoItem t = new TodoItem(new_title, new_category ,new_description, new_due_date);
		l.addItem(t);
		System.out.println("수정되었습니다.");
			
		

	}

	public static void listAll(TodoList l) {
		int count = 1;
		System.out.println("[전체 목록, 총 " + l.getList().size() + "개]");
		for (TodoItem item : l.getList()) {
			System.out.println(count + ". " + item.toString());
			count++;
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
		int count = 0;
		try {
			BufferedReader r = new BufferedReader(new FileReader(filename));
			String data = null;
			while((data = r.readLine())!=null) {
				StringTokenizer stk = new StringTokenizer(data, "##");
				String category = stk.nextToken();
				String title = stk.nextToken();
				String desc = stk.nextToken();
				String due_date = stk.nextToken();
				String current_date = stk.nextToken();
				l.addItem(new TodoItem(category, title, desc, due_date, current_date));
				count++;
			}
			r.close();
			System.out.println(count + "개의 항목을 읽었습니다.");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(filename + " 파일이 없습니다.");
		} catch(IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public static void find(TodoList l, String keyword) {
		int num=1;
		int count = 0;
		
		for(TodoItem item : l.getList()) {
			if(item.getTitle().contains(keyword)||item.getDesc().contains(keyword)) {
				System.out.println(num + ". " + item.toString());
				count++;
			}
			num++;
		}
		System.out.println("총 " + count + "개의 항목을 찾았습니다.");		
	}
	
	public static void find_cate(TodoList l, String keyword) {
		int num=1;
		int count = 0;
		
		for(TodoItem item : l.getList()) {
			if(item.getCategory().contains(keyword)) {
				System.out.println(num + ". " + item.toString());
				count++;
			}
			num++;
		}
		System.out.println("총 " + count + "개의 항목을 찾았습니다.");		
	}
	
	public static void ls_cate(TodoList l) {
		int count = 0;
		HashSet<String> category = new HashSet<String>();
		for(TodoItem item : l.getList()) {
			category.add(item.getCategory());
		}
		for(String to_print : category) {
			if(count==0) {
				System.out.print(to_print);
			}else {
				System.out.print(" / " + to_print);
			}
			count++;
		}
		System.out.println("\n총 " + count + "개의 카테고리가 등록되어 있습니다.");
		
	}
}
