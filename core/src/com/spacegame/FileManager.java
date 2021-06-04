package com.spacegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileManager {

	//objeto de datos del juegador
	public static UserData userData;
	//objeto de la lista de jugadores registrados
	public static UserList userList;


	public static FileHandle fileLista = new FileHandle("UserList.sav");

	private static String userName;


	/**
	 * comprobamos si existe el fichero con los datos del jugador
	 * @return boolean
	 */
	public static boolean existeUserFile(){
		FileHandle file = new FileHandle(userName+".sav");
		return file.exists();
	}

	/**
	 * comprobamos si existe el fichero con la lista de jugadores
	 * @return boolean
	 */
	public static boolean existeUserList(){
		return fileLista.exists();
	}

	/**
	 * crea el fichero si no existe del jugador
	 */
	public static void inicioUser(){
		userData = new UserData();
		salvarUserData();
	}

	/**
	 * crea el fichero si no existe de la lista
	 */
	public static void inicioLista(){
		userList = new UserList();
		salvarUserList();
	}

	/**
	 * método para guardar datos en la lista de usuarios
	 */
	public static void salvarUserList(){

		try{
		ByteArrayOutputStream outByte= new ByteArrayOutputStream();
		DataOutputStream dataOut = new DataOutputStream(outByte);
		ObjectOutputStream outStream = new ObjectOutputStream(dataOut);
		outStream.writeObject(userList);
		outStream.flush();

		byte[] datos = outByte.toByteArray();

		fileLista.writeString(Base64Coder.encodeLines(datos), false);

		}catch (Exception e){
		e.printStackTrace();
		Gdx.app.exit();
		}
	}

	/**
	 * método para cargar datos de la lista de usuarios
	 */
	public static void cargarUserList(){

		try{
			if(!existeUserList()){
				inicioLista();
			}
			ByteArrayInputStream inByte = new ByteArrayInputStream(Base64Coder.decodeLines(fileLista.readString()));
			DataInputStream dataIn = new DataInputStream(inByte);
			ObjectInputStream inStream = new ObjectInputStream(dataIn);

			userList= (UserList) inStream.readObject();


		}catch (Exception e){
			e.printStackTrace();
			Gdx.app.exit();
		}
	}

	/**
	 * método para guardar los datos en el fichero de usuario
	 */
	public static void salvarUserData(){
		try{
			ObjectOutputStream outStream = new ObjectOutputStream(
					new FileOutputStream(userName+".sav"));
			outStream.writeObject(userData);
			outStream.close();

		}catch (Exception e){
			e.printStackTrace();
			Gdx.app.exit();
		}
	}

	/**
	 * método para cargar los datos de el fichero de usuario
	 */
	public static void cargarUserData(){
		try{
			if(!existeUserFile()){
				inicioUser();
				return;
			}
			ObjectInputStream inStream = new ObjectInputStream(
					new FileInputStream(userName+".sav"));
			userData= (UserData) inStream.readObject();
			inStream.close();

		}catch (Exception e){
			e.printStackTrace();
			Gdx.app.exit();
		}
	}

	public static boolean usuarioRepetido(String nombre){
		if (userList != null) {
			for (int i = 0; i < userList.getName().size(); i++) {
				if (userList!= null && userList.getName().get(i).equals(nombre)) {
					return true;
				}
			}
		}
		return false;
	}

	public static void incluirNuevoUser(String user, String pass1) {
		if (userList!= null){
			userList.getName().add(user);
			userList.getPasswords().add(pass1);
			System.out.println("user added");
			Gdx.app.log("user", "user "+ user+ " añadido");
		}
	}

	public static boolean recuperarUserPassWord(String name, String pass) {
		boolean result = false;
		int indice = -1;
		for (int i = 0; i < userList.getName().size(); i++){
			if (name.equals(userList.getName().get(i))){
				indice = i;
			}
			if (indice >= 0) {
				result = (pass.equals(userList.getPasswords().get(indice)));
			}
		}

		return result;
	}
}
