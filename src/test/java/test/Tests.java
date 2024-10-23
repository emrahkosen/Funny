package test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class Tests {

    @Test
    public void getHello() throws Exception {
        System.out.println("Array to ArrayList");
        int[] nums = {1,2,23,53,5,4,6,7};

        Arrays.sort(nums);
        int iterate = 1;
        int max = 1;
        if(nums.length == 0){
            System.out.println("result = " + 0);
        }
        for(int i = 1; i< nums.length; i++){
            if(nums[i] == nums[i-1] + 1){
                iterate++;
            }else{
                if(max < iterate){
                    max = iterate;
                }

                iterate = 1;
            }
        }
        if(max < iterate){
            max = iterate;
        }

        System.out.println("result = " + max);
    }
}
