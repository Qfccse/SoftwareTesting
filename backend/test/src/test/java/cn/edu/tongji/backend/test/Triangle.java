package cn.edu.tongji.backend.test;

public class Triangle {
    public String judgeTriangle(int a,int b,int c){
        String triangle="";
        if(a+b >c && a+c>b && b+c>a){
            if(a==b&&b==c){
                triangle="等边三角形";
            }else if(a!=b && b!=c && a!=c){
                triangle="一般三角形";
            }else{
                triangle="等腰三角形";
            }
        }else{
            triangle="非三角形";
        }
        return triangle;
    }
}
