package com.mike.wordrank;

import org.bukkit.ChatColor;
/**
 * This class needs your help!
 * Every message that is followed by // Google Translate needs to be checked
 *  by a speeker of that language to make sure it is correct. Use the default
 *  line as reference. This will help a lot!
 */
public class Language {

	private static Languages lang;
	
	public Language(WordRank plugin) {
		lang = plugin.getLang();
	}
	
	public enum Languages {
		EN_US, EN_CA, Spanish, French, German;
		
		public static Languages getLanguage(String language) {
			switch(language.toLowerCase()) {
			case "en_us": return Languages.EN_US;
			case "en_ca": return Languages.EN_CA;
			case "spanish": return Languages.Spanish;
			case "french": return Languages.French;
			case "german": return Languages.German;
			default: {
				WordRank.sendErr("Unknown language " + language);
				return Languages.EN_US;
			}
			}
		}
	}
	
	public enum Msg {
		Vault_Not_Found, WR_Disable_Vault, RT_Unknown_Disable, Word_Used_Up, Group_Set_Success, Group_Add_Success,
		Err_Unknown_GT, Already_In_Group, Word_Doesnt_Exist, Help_LN1, Help_Set_Add_Difference, No_Words_Found,
		List_Of_Words, Not_A_Number, Word_Already_Exists, Failed_Add_Word, Group, Relog, Success, No_Permission,
		Been_Removed, Removed, Must_Be_Player, RT_Not_Command;
		
		@Override
		public String toString() {
			switch(this) {
			case Already_In_Group: {
				switch(lang) {
				case EN_CA: return ChatColor.RED + "Sorry, you are already in the group this word grants";
				case French: return ChatColor.RED + "Vous êtes déjà dans le groupe ce mot accorde"; // Google Translate
				case German: return ChatColor.RED + "Sie sind bereits in der Gruppe dieses Wort gewährt"; // Google Translate
				case Spanish: return ChatColor.RED + "Ya estás en el grupo de esta palabra otorga"; // Google Translate
				default: return ChatColor.RED + "You are already in the group this word grants";
				}
			}
			case Err_Unknown_GT: {
				switch(lang) {
				case EN_CA: return ChatColor.RED + "Sorry, you have redeemed the right word, but the Group Word Type is not 'Set' or 'Add'";
				case French: return ChatColor.RED + "Vous avez racheté le mot juste, mais le Group Word Type n'est pas 'Set' ou 'Add'"; // Google translate
				case German: return ChatColor.RED + "Sie haben das richtige Wort eingelöst, aber der Group Word Type ist nicht 'Set' oder 'Add'"; // Google translate
				case Spanish: return ChatColor.RED + "Usted ha redimido la palabra correcta, pero el Group Word Type no es 'Set' o 'Add'"; // Google translate
				default: return ChatColor.RED + "You have redeemed the right word, but the Group Word Type is not 'Set' or 'Add'";
				}
			}
			case Failed_Add_Word: {
				switch(lang) {
				case EN_CA: return ChatColor.RED + "Sorry, but the word failed to be added to the config";
				case French: return ChatColor.RED + "Impossible d'ajouter le mot à la configuration"; // Google translate
				case German: return ChatColor.RED + "Konnte das Wort zur Konfiguration hinzuzufügen"; // Google translate
				case Spanish: return ChatColor.RED + "No se pudo añadir la palabra a la configuración"; // Google translate
				default: return ChatColor.RED + "Failed to add the word to the config";
				}
			}
			case Group_Add_Success: {
				switch(lang) {
				case French: return ChatColor.GOLD + "Le groupe suivant a été ajouté à la liste de vos groupes: "; // Google translate
				case German: return ChatColor.GOLD + "Die folgende Gruppe wurde in die Liste Ihrer Gruppen wurden hinzugefügt: "; // Google translate
				case Spanish: return ChatColor.GOLD + "El siguiente grupo ha sido añadido a la lista de sus grupos: "; // Google translate
				default: return ChatColor.GOLD + "The following group has been added to the list of your groups: ";
				}
			}
			case Group_Set_Success: {
				switch(lang) {
				case French: return ChatColor.GOLD + "Votre groupe a été mis à la suivante et tous les autres groupes ont été retirés de votre nom: "; // Google translate
				case German: return ChatColor.GOLD + "Ihre Gruppe wurde in den folgenden gesetzt und alle anderen Gruppen haben aus Ihrem Namen wurden entfernt: "; // Google translate
				case Spanish: return ChatColor.GOLD + "Su grupo se ha establecido lo siguiente y todos los demás grupos se han eliminado de su nombre: "; // Google translate
				default: return ChatColor.GOLD + "Your group has been set to the following and all other groups have been removed from your name: ";
				}
			}
			case Relog: {
				switch(lang) {
				case EN_CA: return ChatColor.RED + "Sorry, you may have to reconnect for changes to take effect.";
				case French: return ChatColor.RED + "Vous pourriez avoir à vous reconnecter pour que les changements prennent effet."; // Google translate
				case German: return ChatColor.RED + "Sie müssen sich für die Änderungen wirksam werden zu verbinden."; // Google translate
				case Spanish: return ChatColor.RED + "Puede que tenga que volver a conectar para que los cambios surtan efecto."; // Google translate
				default: return ChatColor.RED + "You may have to reconnect for changes to take effect.";
				}
			}
			case Group: {
				switch(lang) {
				case French: return "Groupe: "; // Google translate
				case German: return "Gruppe: "; // Google translate
				case Spanish: return "Grupo: "; // Google translate
				default: return "Group: ";
				}
			}
			case Success: {
				switch(lang) {
				case French: return ChatColor.GREEN + "Succès!"; // Google translate
				case German: return ChatColor.GREEN + "Erfolg!"; // Google translate
				case Spanish: return ChatColor.GREEN + "éxito!"; // Google translate
				default: return ChatColor.GREEN + "Success!";
				}
			}
			case Help_LN1: {
				switch(lang) {
				case French: return ChatColor.DARK_RED + "WordRank commandes que vous pouvez utiliser"; // Google translate
				case German: return ChatColor.DARK_RED + "WordRank Befehle Sie verwenden können"; // Google translate
				case Spanish: return ChatColor.DARK_RED + "Comandos WordRank que se pueden usar"; // Google translate
				default: return ChatColor.DARK_RED + "|-- WordRank Commands You Can Use --|";
				}
			}
			case Help_Set_Add_Difference: {
				switch(lang) {
				case French: return ChatColor.GOLD + "Set(Défaut) Définit groupe et supprime les autres groupes | Add: ajoute le groupe partant anciens groupes | Uses: -1(Défaut) pour un nombre illimité"; // Google translate
				case German: return ChatColor.GOLD + "Set(Standard) Stellt Gruppe und anderen Gruppen entfernt | Add: fügt die Gruppe verlassen alten Gruppen | Uses: -1(Standard) für unbegrenzte"; // Google translate
				case Spanish: return ChatColor.GOLD + "Set(Defecto) Establece grupo y elimina otros grupos | Add: agrega el grupo saliente grupos de edad | Uses: -1(Defecto) para un número ilimitado"; // Google translate
				default: return ChatColor.GOLD + "Set(Default): Sets group, removes other groups | Add: adds the group leaving old group(s) | Uses: -1(Default) for unlimited";
				}
			}
			case List_Of_Words: {
				switch(lang) {
				case French: return ChatColor.AQUA + "Liste des mots: "; // Google translate
				case German: return ChatColor.AQUA + "Liste der Wörter: "; // Google translate
				case Spanish: return ChatColor.AQUA + "Lista de palabras: "; // Google translate
				default: return ChatColor.AQUA + "List of words: ";
				}
			}
			case No_Words_Found: {
				switch(lang) {
				case French: return ChatColor.RED + "Il n'y a pas de mots!"; // Google translate
				case German: return ChatColor.RED + "Es gibt keine Worte!"; // Google translate
				case Spanish: return ChatColor.RED + "No hay palabras!"; // Google translate
				default: return ChatColor.RED + "There are no words!";
				}
			}
			case Not_A_Number: {
				switch(lang) {
				case EN_CA: return ChatColor.RED + "Sorry, you provided a string where a number should have been";
				case French: return ChatColor.RED + "Vous avez fourni une lettre ou un mot où un certain nombre aurait dû être"; // Google translate
				case German: return ChatColor.RED + "Sie versehen einen Brief oder ein Wort, wo eine Reihe gewesen sein sollte"; // Google translate
				case Spanish: return ChatColor.RED + "Usted siempre una letra o palabra en un número que debería haber sido"; // Google translate
				default: return ChatColor.RED + "You provided a string where a number should have been";
				}
			}
			case RT_Unknown_Disable: {
				switch(lang) {
				case EN_CA: return "Sorry, the redeem type is unknown, WordRank will disable. Check the config and make sure 'redeem_type' is set to 'Chat' or 'Command'";
				case French: return "Redeem type est inconnue, WordRank sera désactivé. Vérifiez la configuration et assurez-vous que tout est ok."; // Google translate
				case German: return "Redeem type unbekannt ist, wird WordRank deaktivieren. Überprüfen Sie die Konfiguration und stellen Sie sicher, dass alles in Ordnung ist."; // Google translate
				case Spanish: return "Redeem type es desconocida, WordRank desactivará. Compruebe la configuración y asegúrese de que todo está bien."; // Google translate
				default: return "Redeem type is unknown, WordRank will disable. Check the config and make sure 'redeem_type' is set to 'Chat' or 'Command'";
				}
			}
			case Vault_Not_Found: {
				switch(lang) {
				case EN_CA: return "Sorry, the required plugin 'Vault' is not enabled, installed, or working properly. This plugin is required for WordRank to operate!";
				case French: return "Le plug-in required 'Vault' n'est pas activé, installé ou fonctionner correctement. Ce plugin est requis pour WordRank de fonctionner!"; // Google translate
				case German: return "Die erforderliche Plugin 'Vault' ist nicht aktiviert, installiert oder richtig funktioniert. Dieses Plugin ist für WordRank zu bedienen erforderlich!"; // Google translate
				case Spanish: return "El plugin required 'Vault' no está habilitada, instalado o funciona correctamente. Se requiere este plugin para WordRank funcione!"; // Google translate
				default: return "The required plugin 'Vault' is not enabled, installed, or working properly. This plugin is required for WordRank to operate!";
				}
			}
			case WR_Disable_Vault: {
				switch(lang) {
				case EN_CA: return "Sorry, WordRank will now disable and will check for the plugin 'Vault' next time it's enabled.";
				case French: return "WordRank va maintenant désactiver et vérifier pour le plugin 'Vault' la prochaine fois qu'il est activé."; // Google translate
				case German: return "WordRank wird nun deaktiviert und wird für das Plugin 'Vault' beim nächsten Mal wird es aktiviert zu überprüfen."; // Google translate
				case Spanish: return "WordRank ahora se inhabilitará y comprobará la próxima vez que el plug-in 'Vault' está habilitado."; // Google translate
				default: return "WordRank will now disable and will check for the plugin 'Vault' next time it's enabled.";
				}
			}
			case Word_Already_Exists: {
				switch(lang) {
				case EN_CA: return ChatColor.RED + "Sorry, that word already exists";
				case French: return ChatColor.RED + "Ce mot existe déjà."; // Google translate
				case German: return ChatColor.RED + "Dieses Wort existiert."; // Google translate
				case Spanish: return ChatColor.RED + "Ya existe esa palabra."; // Google translate
				default: return ChatColor.RED + "That word already exists";
				}
			}
			case Word_Doesnt_Exist: {
				switch(lang) {
				case EN_CA: return ChatColor.RED + "Sorry, that word doesn't exist";
				case French: return ChatColor.RED + "Ce mot n'existe pas."; // Google translate
				case German: return ChatColor.RED + "Dieses Wort existiert nicht."; // Google translate
				case Spanish: return ChatColor.RED + "Esa palabra no existe."; // Google translate
				default: return ChatColor.RED + "That word doesn't exist";
				}
			}
			case Word_Used_Up: {
				switch(lang) {
				case EN_CA: return ChatColor.RED + "Sorry, this word is out of uses";
				case French: return ChatColor.RED + "Ce mot est des utilisations!"; // Google translate
				case German: return ChatColor.RED + "Dieses Wort ist aus der Anwendung!"; // Google translate
				case Spanish: return ChatColor.RED + "Esta palabra es de uso!"; // Google translate
				default: return ChatColor.RED + "This word is out of uses!";
				}
			}
			case No_Permission: {
				switch(lang) {
				case EN_CA: return ChatColor.RED + "Sorry, you do not have permission for this";
				case French: return ChatColor.RED + "Vous n'avez pas l'autorisation pour cette"; // Google translate
				case German: return ChatColor.RED + "Sie haben keine Berechtigung für diese"; // Google translate
				case Spanish: return ChatColor.RED + "No tiene permiso para esto"; // Google translate
				default: return ChatColor.RED + "You do not have permission for this";
				}
			}
			case Been_Removed: {
				switch(lang) {
				case French: return " a été retiré"; // Google translate
				case German: return " entfernt wurde"; // Google translate
				case Spanish: return " se ha eliminado"; // Google translate
				default: return " has been removed";
				}
			}
			case Removed: {
				switch(lang) {
				case French: return " supprimé"; // Google translate
				case German: return " entfernt"; // Google translate
				case Spanish: return " eliminado"; // Google translate
				default: return " word(s) removed";
				}
			}
			case Must_Be_Player: {
				switch(lang) {
				case EN_CA: return ChatColor.RED + "Sorry, you must be a player to redeem";
				case French: return ChatColor.RED + "Vous devez être un joueur de racheter"; // Google translate
				case German: return ChatColor.RED + "Sie muss ein Spieler sein, einlösen"; // Google translate
				case Spanish: return ChatColor.RED + "Usted debe ser un jugador para redimir"; // Google translate
				default: return ChatColor.RED + "You must be a player to redeem";
				}
			}
			case RT_Not_Command: {
				switch(lang) {
				case EN_CA: return ChatColor.RED + "Sorry, redeem type is not set to 'Command'";
				case French: return ChatColor.RED + "Redeem type n'est pas configuré pour 'Command'"; // Google translate
				case German: return ChatColor.RED + "Redeem type nicht auf 'Command' eingestellt"; // Google translate
				case Spanish: return ChatColor.RED + "Redeem type no está establecido en 'Command'"; // Google translate
				default: return ChatColor.RED + "Redeem type is not set to 'Command'";
				}
			}
			}
			return null;
		}
	}
}