package com.spacegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class FileManager {

	//objeto de datos del juegador
	public static UserData userData;
	//objeto de la lista de jugadores registrados
	public static UserList userList;

	private static String userName;

	public static FileHandle fileLista = Gdx.files.local("UserList.sav");
	public static FileHandle userFile = Gdx.files.local(userName+".sav");


	/**
	 * comprobamos si existe el fichero
	 * @return boolean
	 */
	public static boolean existeFile(FileHandle file){
		return file.exists();
	}


	/**
	 * crea el fichero si no existe del jugador
	 */
	public static void inicioUser(String userName){
		userData = new UserData(userName);
		userData.inciar();
		userFile = Gdx.files.local(userName+".sav");
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
			if(!existeFile(fileLista)){
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
			ByteArrayOutputStream outByte= new ByteArrayOutputStream();
			DataOutputStream dataOut = new DataOutputStream(outByte);
			ObjectOutputStream outStream = new ObjectOutputStream(dataOut);
			outStream.writeObject(userData);
			outStream.flush();

			byte[] datos = outByte.toByteArray();
			userFile.writeString(Base64Coder.encodeLines(datos), false);

		}catch (Exception e){
			e.printStackTrace();
			Gdx.app.exit();
		}
	}

	/**
	 * llama a sobrecargado que usa el string con el nombre
	 * para coger el fileHandel y enviarlo como parametro
	 * @param usuario String nombre Usuario
	 * @return UserData
	 */
	public static UserData cargarUserData(String usuario){
		return cargarUserData(setNombreFicheroUser(usuario));
	}

	/**
	 * método para cargar los datos de el fichero de usuario
	 * @param file FileHandel con el fichero a cargar
	 * @return UserData con los datos guardados del usuario
	 */
	public static UserData cargarUserData(FileHandle file){
		try{
			if(!existeFile(userFile)){
				inicioUser(userName);
			}
			ByteArrayInputStream inByte = new ByteArrayInputStream(Base64Coder.decodeLines(userFile.readString()));
			DataInputStream dataIn = new DataInputStream(inByte);
			ObjectInputStream inStream = new ObjectInputStream(dataIn);
			 userData= (UserData) inStream.readObject();
		}catch (Exception e){
			e.printStackTrace();
			Gdx.app.exit();
		}
		return userData;
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

	/**
	 * inserta usuarios en el fichero de usuarios
	 * @param user String nombre user
	 * @param pass1 String contraseña
	 */
	public static void incluirNuevoUser(String user, String pass1) {
		if (userList!= null){
			userList.getName().add(user);
			userList.getPasswords().add(pass1);
			Gdx.app.log("user", "user "+ user+ " añadido");
		}
	}

	/**
	 * comprueba si el nombre y la contraseña coinciden en el fichero de usuarios
	 * @param name String nombre user
	 * @param pass String contraseña
	 * @return Boolean
	 */
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

	/**
	 * recupera la contraseña según el nombre
	 * @param name String nombre user
	 * @return String con el password
	 */
	public static String getPassWord(String name) {
		String pass = null;
		int indice = -1;
		for (int i = 0; i < userList.getName().size(); i++){
			if (name.equals(userList.getName().get(i))){
				indice = i;
			}
			if (indice >= 0) {
				pass = (userList.getPasswords().get(indice));
			}
		}
		return pass;
	}

	public static void setPassWord (String name, String pass){
		int indice = -1;
		for (int i = 0; i < userList.getName().size(); i++){
			if (name.equals(userList.getName().get(i))){
				indice = i;
			}
			if (indice >= 0) {
			 userList.getPasswords().set(indice, pass);
			}
		}
	}

	public static UserData getUserData() {
		return userData;
	}

	public static void setUserData(UserData userData) {
		FileManager.userData = userData;
	}

	/**
	 * asigna el nombre al archivo para ver si exite
	 * @param nombre String con el nombre de usuario
	 * @return fichero con el nombre del usuario
	 */
	public static FileHandle setNombreFicheroUser(String nombre){
		userFile = Gdx.files.local(nombre+".sav");
		return userFile;
	}

}
