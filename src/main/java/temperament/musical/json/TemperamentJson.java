package temperament.musical.json;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import temperament.musical.ITemperament;
import temperament.musical.MusicalKnowledge;
import temperament.musical.NotesInterval;

/**
 * tempérament basé sur le contenu d'un fichier JSON
 */
public class TemperamentJson implements ITemperament {
	private String				name		= null;
	private String				description	= null;
	private ArrayList<JSonNote>	notes		= null;

	public TemperamentJson(Path path) {
		try {
			String s = Files.readString(path);
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
		}
	}

	public TemperamentJson(String path) {
		this(Path.of(path));
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
		return MusicalKnowledge.dansOctave(getNoteFrequencyRatioInFifthsCirle(noteIndex));
	}

	@Override
	public double getNoteFrequencyRatioInFifthsCirle(int noteIndex) {
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
	public int La() {
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
	public int getNbNotesToPlayGamme() {
		return getNbNotes();
	}

	@Override
	public double getFrequenceDo(double frequenceLa) {
		return frequenceLa / getNoteFrequencyRatio(La());
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

	@Override
	public List<NotesInterval> getFifthsIntervals() {
		return null;
	}

	@Override
	public List<NotesInterval> getMajorThirdsIntervals() {
		return null;
	}

	@Override
	public String getNoteFullName(int noteIndex) {
		return getNoteName(noteIndex);
	}

	@Override
	public int findNoteIndexByFullName(String fullName) {
		for (int i = 0; i < getNbNotes(); i++) {
			String n = getNoteFullName(i);
			if (n.equals(fullName))
				return i;
		}
		return -1;
	}

	@Override
	public int findNoteIndexByRatio(double ratio) {
		int bestIndex = -1;
		double bestDiff = 1000;
		for (int i = 0; i < getNbNotes(); i++) {
			double noteRatio = getNoteFrequencyRatio(i);
			double diff = Math.abs(ratio - noteRatio);
			if (diff < bestDiff) {
				bestDiff = diff;
				bestIndex = i;
			}
		}
		return bestDiff < 0.1 ? bestIndex : -1;
	}

	@Override
	public boolean isModernTemperament() {
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
