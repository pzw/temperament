package temperament.musical.json;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import temperament.musical.ITemperament;

public class TemperamentJsonVisitor {

	/**
	 * retourne une liste de tempérament définis par des fichiers .json d'un
	 * répertoire
	 * 
	 * @param rootPath dossier à explorer
	 * @return une liste de tempéraments trouvés dans un dossier.
	 */
	public List<ITemperament> buildTemperaments(Path rootPath) {
		ArrayList<ITemperament> result = new ArrayList<ITemperament>();
		try {
			Files.walkFileTree(rootPath, new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					String fileName = file.getFileName().toString();
					if (fileName.endsWith(".json")) {
						TemperamentJson t = new TemperamentJson(file);
						if (t.isWellDefined()) {
							result.add(t);
						}
					}
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
