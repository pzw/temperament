package temperament.musical.json;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import temperament.musical.ITemperament;

/**
 * tempérament basé sur le contenu d'un fichier JSON (actuellement pas utilisé)
 */
public class TemperamentJson implements ITemperament {
	private String				name		= null;
	private String				description	= null;
	private ArrayList<JSonNote>	notes		= null;

	public TemperamentJson(String path) {
		try {
			String s = Files.readString(Path.of(path));
			JSONObject def = new JSONObject(s);
			name = def.getString("name");
			description = def.getString("description");
			JSONArray jsonNotes = def.getJSONArray("notes");
			notes = new ArrayList<TemperamentJson.JSonNote>();
			for (int i = 0; i < jsonNotes.length(); i++) {
				JSONObject n = jsonNotes.getJSONObject(i);
				JSonNote note = new JSonNote(n);
				notes.add(note);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getNbNotes() {
		return null == notes ? 0 : notes.size();
	}

	private int checkIndex(int noteIndex) {
		if (null == notes)
			return -1;
		if (noteIndex >= notes.size())
			return -1;
		return noteIndex;

	}

	@Override
	public double getNoteFrequencyRatio(int noteIndex) {
		int idx = checkIndex(noteIndex);
		if (idx < 0)
			return 1.0;
		return notes.get(noteIndex).getNoteFrequencyRatio();
	}

	@Override
	public String getNoteName(int noteIndex) {
		int idx = checkIndex(noteIndex);
		if (idx < 0)
			return "unknown";
		return notes.get(noteIndex).name;
	}

	@Override
	public int getIndexLa() {
		if (null == notes)
			return 0;
		for (int i = 0; i < notes.size(); i++) {
			if ("la".equals(notes.get(i).name)) {
				return i;
			}
		}
		return 0;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int getNbNotesGamme() {
		return getNbNotes();
	}

	@Override
	public double getFrequenceDo(double frequenceLa) {
		return frequenceLa / getNoteFrequencyRatio(getIndexLa());
	}

	public boolean isWellDefined() {
		if (null == name)
			return false;
		if (null == description)
			return false;
		if (null == notes)
			return false;
		for (JSonNote n : notes) {
			if (!n.isWellDefined())
				return false;
		}
		return true;
	}

	private class JSonNote {
		private String	name;
		private double	ratioMul;
		private double	ratioDiv;

		public JSonNote(JSONObject o) {
			name = o.getString("name");
			ratioMul = o.getDouble("ratio_mul");
			ratioDiv = o.getDouble("ratio_div");
		}

		public boolean isWellDefined() {
			return name != null && name.length() > 0 && ratioMul > 0.0 && ratioDiv > 0.0;
		}

		public double getNoteFrequencyRatio() {
			return ratioMul / ratioDiv;
		}
	}
}
