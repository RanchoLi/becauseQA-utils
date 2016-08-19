package com.github.becauseQA.algorithm;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.github.becauseQA.apache.commons.RandomUtils;

public class FibonacciTest {
	public static final int MONTH = 15;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void test() {
		List arrayData = new ArrayList<>();

		for (int k = 0; k < 10; k++) {

			arrayData.add(RandomUtils.nextInt(1, 2000));
		}
		System.out.println(arrayData.toString());

		// Collections.sort(arrayData);
		// Collections.reverse(arrayData);
		System.out.println(arrayData);

		Integer[] array = (Integer[]) arrayData.toArray(new Integer[arrayData.size()]);

		BubbleSortDesc(array);
		System.out.println(array);

	}

	public Integer[] BubbleSortAsc2(Integer[] arrays) {

		// a_n=a_1+(n-1)d
		int size = arrays.length;
		int temp = 0;
		for (int k = 0; k < size; k++) {
			for (int j = 1; j < size - k; j++) {
				if (arrays[j - 1] > arrays[j]) {
					temp = arrays[j - 1];
					arrays[j - 1] = arrays[j];
					arrays[j] = temp;

				}
			}
		}
		// System.out.println(arrays);
		return arrays;
	}

	// 冒泡排序算法 http://blog.csdn.net/pzhtpf/article/details/7560294
	public static Integer[] BubbleSortAsc(Integer[] list) {
		int temp;
		// 第一层循环： 表明要比较的次数，比如list.count个数，肯定要比较count-1次
		for (int i = 0; i < list.length - 1; i++) {
			// list.count-1：取数据最后一个数下标，
			// j>i: 从后往前的的下标一定大于从前往后的下标，否则就超越了。
			for (int j = list.length - 1; j > i; j--) {
				// 如果前面一个数大于后面一个数则交换
				if (list[j - 1] > list[j]) {
					temp = list[j - 1];
					list[j - 1] = list[j];
					list[j] = temp;
				}
			}
		}
		return list;
	}

	// 冒泡排序算法
	public static Integer[] BubbleSortDesc(Integer[] list) {
		int temp;
		// 第一层循环： 表明要比较的次数，比如list.count个数，肯定要比较count-1次
		for (int i = 0; i < list.length - 1; i++) {
			// list.count-1：取数据最后一个数下标，
			// j>i: 从后往前的的下标一定大于从前往后的下标，否则就超越了。
			for (int j = list.length - 1; j > i; j--) {
				// 如果前面一个数大于后面一个数则交换
				if (list[j - 1] < list[j]) {
					temp = list[j - 1];
					list[j - 1] = list[j];
					list[j] = temp;
				}
			}
		}
		return list;
	}

	/**
	 * 题目：打印出所有的"水仙花数"，所谓"水仙花数"是指一个三位数，其各位数字立方和等于该数本身。
	 * 例如：153是一个"水仙花数"，因为153=1的三次方＋5的三次方＋3的三次方。
	 * 1.程序分析：利用for循环控制100-999个数，每个数分解出个位，十位，百位。
	 * 
	 * @author alter
	 */
	public static void daffodils() {
		int a, b, c;
		int data;
		for (int i = 100; i < 999; i++) {
			a = i / 100;
			b = (i - 100 * a) / 10;
			c = i - 100 * a - 10 * b;
			data = a * a * a + b * b * b + c * c * c;
			if (data == i) {
				System.out.println(i);
			}
		}
	}

	@Test
	public void findRibbat() {

		long f1 = 1L, f2 = 1L;
		long f;
		for (int i = 3; i < MONTH; i++) {
			f = f1 + f2;
			f1 = f2;
			f2 = f;
			System.out.println("第" + i + "个月的兔子对数：" + f2);
		}

		/*
		 * for(int i =1 ; i<MONTH; i++){
		 * System.out.println("第"+i+"个月的兔子对数："+fib(i)); }
		 */
	}
	// 递归方法实现

	/**
	 * * 兔子问题 斐波那契数列求值
	 * 
	 * @author tonylp 题目：古典问题：有一对兔子，从出生后第3个月起每个月都生一对兔子，
	 *         小兔子长到第三个月后每个月又生一对兔子，假如兔子都不死，问每个月的兔子总数为多少？ 1.程序分析：
	 *         兔子的规律为数列1,1,2,3,5,8,13,21....
	 * @param month
	 * @return
	 */
	public static int fib(int month) {
		if (month == 1 || month == 2) {
			return 1;
		} else {
			return fib(month - 1) + fib(month - 2);
		}
	}

	/*
	 * http://www.cnblogs.com/huangxincheng/archive/2012/08/05/2624156.html
	 * 百钱买百鸡的问题算是一套非常经典的不定方程的问题，题目很简单：公鸡5文钱一只，母鸡3文钱一只，小鸡3只一文钱，
	 * 
	 * 用100文钱买一百只鸡,其中公鸡，母鸡，小鸡都必须要有，问公鸡，母鸡，小鸡要买多少只刚好凑足100文钱。
	 * 
	 * 
	 * 
	 * 分析：估计现在小学生都能手工推算这套题，只不过我们用计算机来推算，我们可以设公鸡为x，母鸡为y，小鸡为z，那么我们
	 * 
	 * 可以得出如下的不定方程，
	 * 
	 * x+y+z=100,
	 * 
	 * 5x+3y+z/3=100，
	 * 
	 * 下面再看看x，y，z的取值范围。
	 * 
	 * 由于只有100文钱，则5x<100 => 0<x<20, 同理 0<y<33,那么z=100-x-y，
	 */
	@Test
	public void getChiken() {
		for (int x = 1; x < 20; x++) {
			for (int y = 0; y < 33; y++) {

				int z = 100 - x - y;
				if (z % 3 == 0 && (5 * x + 3 * y + z / 3 == 100)) {
					System.out.println("x=" + x + ",y=" + y + ",z=" + z);
				}
			}
		}
	}

	/*
	 * http://www.cnblogs.com/huangxincheng/archive/2012/08/06/2625427.html
	 * 意思就是说五家人共用一口井，甲家的绳子用两条不够，还要再用乙家的绳子一条才能打到井水；乙家的绳子用三条不够，还要再用丙家的绳子
	 * 
	 * 一条才能打到井水；丙家的绳子用四条不够，还要再用丁家的绳子一条才能打到井水；丁家的绳子用五条不够，还要再用戊家的绳子一条才能打
	 * 
	 * 到井水；戊家的绳子用六条不够，还要再用甲家的绳子一条才能打到井水。
	 * 
	 * 最后问：井有多深？每家的绳子各有多长？
	 * 分析：同样这套题也是属于不定方程，拿这个题目的目地就是让大家能够在不定方程组这种范畴问题上做到“举一反三”，根据题意
	 * 
	 * 我们设井深为h，各家分别为a,b,c,d,e，则可以列出如下方程组：
	 * 
	 * 2a+b=h ①
	 * 
	 * 3b+c=h ②
	 * 
	 * 4c+d=h ③
	 * 
	 * 5d+e=h ④
	 * 
	 * 6e+a=h ⑤
	 * 
	 * 首先我们看下普通青年的想法，他们的想法是找a,b,c,d,e之间的对应关系。
	 * 
	 * 依次将②代入①，③代入②，④代入③，⑤代入④可得如下方程组：
	 * 
	 * a=b+c/2
	 * 
	 * b=c+d/3
	 * 
	 * c=d+e/4
	 * 
	 * d=e+a/5
	 * 
	 * 从计算机的角度来说，我不希望有小数的出现，所以我可推断： c一定是2的倍数，d一定是3的倍数，e一定是4的倍数，a一定是5的倍数，根据这种关系我们
	 * c=(148/721)h
	 * 
	 * 上面的公式也就表明了c和h的比例关系，我们令 h=721k,则 c=148k，将其代入⑥，⑦，⑧，⑨，⑩可得如下方程组
	 * 
	 * a=265k
	 * 
	 * b=191k
	 * 
	 * c=148k
	 * 
	 * d=129k
	 * 
	 * e=76k
	 * 
	 * x=721k
	 * 
	 * 又因为k>0，所以题目有无数个解。这里我就取0<k<5
	 */
	@Test
	public void getWellNumber() {
		for (int k = 1; k < 5; k++) {
			int h = 721 * k;

			int a = 265 * k;

			int b = 191 * k;

			int c = 148 * k;

			int d = 129 * k;

			int e = 76 * k;

			System.out.println("a=" + a + ",b=" + b + ",c=" + c + ",d=" + d + ",e=" + e + ",h=" + h);
		}
	}

	/*
	 * 算法中有一种叫做“递推思想”，转化到数学上来说就是“数列”，而我们苦逼的coding，复杂度搞死也只能控制在O(N)，但有没有
	 * 
	 * 想过对这种问题可以一针见血，一刀毙命,这就需要用到“数学”上的知识。猴子吃桃 问题就是一个活生生的例子
	 * http://www.cnblogs.com/huangxincheng/archive/2012/08/08/2628022.html
	 * http://www.cnblogs.com/huangxincheng/archive/2012/09/07/2674439.html
	 * 猴子第一天摘下若干个桃子，当即吃了一半，还不过瘾就多吃了一个。第二天早上又将剩下的桃子吃了一半，还是不过瘾又多
	 * 吃了一个。以后每天都吃前一天剩下的一半再加一个。到第10天刚好剩一个。问猴子第一天摘了多少个桃子？
	 * 这个题目体现了算法思想中的递推思想，递归有两种形式，顺推和逆推，针对递推
	 * 
	 */
	@Test
	public void getPeach() {
		int sumPeach = sumPeach();
		System.out.println("total is: " + sumPeach);
	}

	public int sumPeach(int day) {
		if (day == 10) {
			return 1;
		}
		return 2 * sumPeach(day + 1) + 2;

	}

	public int sumPeach2(int day, int total) {
		if (day == 10) {
			return total;
		}
		return sumPeach2(day - 1, 2 * total + 2);

	}

	public int sumPeach() {
		int total = 1;
		for (int k = 1; k <= 10; k++) {
			total = 2 * total + 2;
		}
		return total;
	}
	
	@Test
	public void getPower(){
		double pow = Math.pow(3, 3);
		System.out.println(pow);
		
	}

}
