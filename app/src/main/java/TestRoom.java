import android.arch.persistence.room.Room;
import android.content.Context;

import com.geopostcards.cleblond2016.geopostcards.BO.User;
import com.geopostcards.cleblond2016.geopostcards.DAO.UserDAO;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class TestRoom {
        private UserDAO mUserDao;
        private TestRoom mDb;

        @Before
        public void createDb() {
            Context context = InstrumentationRegistry.getTargetContext();
            mDb = Room.inMemoryDatabaseBuilder(context, TestDatabase.class).build();
            mUserDao = mDb.getUserDao();
        }

        @After
        public void closeDb() throws IOException {
            mDb.close();
        }

        @Test
        public void writeUserAndReadInList() throws Exception {
            User user = new User("Vulkain", "rgrlig54rsdhsrhqer4qe5h4", "vulkain@gmail.com", "mdpSecret1");
            mUserDao.inse;
            List<User> byName = mUserDao.findUsersByName("george");
            assertThat(byName.get(0), equalTo(user));
        }
    }

}
