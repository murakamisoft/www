//�����_���Ȑ��𐶐�
public class RandomNumber{
	
	//�ŏ��l�ƍő�l�̊ԂŃ����_����int�^���l��Ԃ�
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