package com.itcast.entity;

public class Student implements Comparable<Student> {

	private String name;

	private int score;

	public Student(String name, int score) {
		this.name = name;
		this.score = score;
	}

	// ����Student��compareTo����
	// ���������ȣ��������ֵ���ĸ������
	// �����������ȣ�������ߵĿ�ǰ
	@Override
	public int compareTo(Student xjx) {
		
		if(this.score < xjx.score)
			return -1;
		else if(this.score > xjx.score)
			return 1;
		else
			return this.name.compareTo(xjx.name);
	
	}

	 // ����Studentʵ���Ĵ�ӡ�����ʽ
    @Override
    public String toString() {
        return "Student: " + this.name + " " + Integer.toString( this.score );
    }
	
	

}
