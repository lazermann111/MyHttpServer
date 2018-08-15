import com.lazermann.httpserver.model.TopResults;
import com.lazermann.httpserver.model.UserResult;
import com.lazermann.httpserver.repositories.UserResultRepository;
import com.lazermann.httpserver.repositories.UserResultRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.lazermann.httpserver.Constants.MAX_RESULTS_SIZE;

public class BaseTests
{

    @Test
    public void userRepositoryTests()
    {
        UserResultRepository repository = new UserResultRepositoryImpl();
        String level_id = "1";
        String user_id = "1";
        int results_count = MAX_RESULTS_SIZE + 10;

        for (int i = 1; i <= results_count; i++)
        {
            for (int j = 1; j <= results_count; j++)
            {
                UserResult result = new UserResult(i + "", level_id, i * j);
                repository.saveUserResult(result);
            }
        }

        Assertions.assertEquals(repository.getTopLevelInfo(level_id).size(), MAX_RESULTS_SIZE) ;
        Assertions.assertEquals(repository.getTopLevelInfo("-1").size(),0 );

        Assertions.assertEquals(repository.getTopUserInfo(user_id).size(), MAX_RESULTS_SIZE) ;
        Assertions.assertEquals(repository.getTopUserInfo("-1").size(),0 );


        repository.saveUserResult(new UserResult(level_id, user_id, 5000));

        Assertions.assertEquals(repository.getTopLevelInfo(level_id).iterator().next().getResult(), 5000) ;
        Assertions.assertEquals(repository.getTopUserInfo(level_id).iterator().next().getResult(), 5000) ;
    }

    @Test
    public void topResultsTest()
    {
        TopResults results = new TopResults();

        for (int i = 1; i <= MAX_RESULTS_SIZE; i++)
        {
            UserResult r = new UserResult(i + "", i + "", i * 50);
            results.addNewResult(r);
        }

        Assertions.assertEquals( results.getResults().first().getResult(),MAX_RESULTS_SIZE * 50 );

        results.addNewResult(new UserResult("1", "1", 5000));

        Assertions.assertEquals( results.getResults().first().getResult(),5000 );
        Assertions.assertEquals( results.getResults().size(), MAX_RESULTS_SIZE );
    }

}
