//ランダムな数を生成
public class RandomNumber{
	
	//最小値と最大値の間でランダムなint型数値を返す
	public int getNumber(int lower, int higher){
		double num;
		double difference;
		
		do{
			difference = higher - lower;
			num = (difference * (Math.random())) + lower;
		}while(num < lower || num > higher);
		
		return (int)num;
	}
}