import me.lukis.plugin.database.models.PlayerDropSettings;
import me.lukis.plugin.database.repositories.SettingsRepository;
import org.junit.jupiter.api.BeforeAll;

public class RepositoryTest {

    private static final SettingsRepository settingsRepository = new SettingsRepository();

    @BeforeAll
    public static void defaults() {
        PlayerDropSettings playerDropSettings = new PlayerDropSettings();
        settingsRepository.addPlayerSettings("LUKIS", playerDropSettings);
        settingsRepository.addPlayerSettings("SERA", playerDropSettings);
    }
}