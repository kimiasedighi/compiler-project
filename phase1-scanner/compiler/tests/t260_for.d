int main(){
    int x;
    x = 2;
    for(; true; ){
		x = x+1;
		if(x==4){
			break;
		}else
		{
            x=x%2;
		    break;
		}

		Print(x);
    }
}