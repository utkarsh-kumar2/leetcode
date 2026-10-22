class Solution {
    public void reverseString(char[] s) {
        int n=s.length;
        char[] v =new char[n];
        
        for(int i=n-1;i>=0;i--){
            v[i]=s[n-1-i];
        }
        for(int i=0;i<n;i++){
            s[i]=v[i];
        }
        
        
    }
}