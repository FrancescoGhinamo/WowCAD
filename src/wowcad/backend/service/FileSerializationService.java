package wowcad.backend.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Implementation of ISerializationSerice
 * @author franc
 *
 */
public class FileSerializationService implements ISerializationService {

	
	FileSerializationService() {
		
	}
	
	@Override
	public void serialize(Object what, String destination) {
		File dest = new File(destination);
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(dest));
			
			oos.writeObject(what);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(oos != null) {
				try {
					oos.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally {
					try {
						oos.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

	}

	@Override
	public Object deserialize(String source) {
		Object res = null;
		File s = new File(source);
		ObjectInputStream ois = null;
		
		try {
			ois = new ObjectInputStream(new FileInputStream(s));
			
			res = ois.readObject();
			
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return res;
	}

}
