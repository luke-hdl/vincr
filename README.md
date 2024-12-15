# Vincr

Vincr is a [libGDX](https://libgdx.com/)-based application for calculating Vinc values for Vampire: The Masquerade game. It was developed as a quick prototype for a local LARP, so the error handling is fairly limited (though common errors do have help text.) Further work is certainly possible. This tool uses the House Rules for the Swift to Shed Blood LARP in Minneapolis, Minnesota; you are welcome to use it, as long as you don't mind that. Vincr is licensed under the GNU GPL license. 

The custom ruleset for Vincr is: 
If your Vinc value is 0, 1, or 2: 1d20+5 >= 10 gains Vinc, otherwise loses.
Otherwise: 1d20+(6-your current Vinc value) >= 10 gains Vinc, otherwise loses.  

The program has its own help text. It reads a CSV file picked with a file picker, than writes a CSV file with the new values.

## Packaging
Vincr is packaged using Packr. The command used is:
java -jar packr-all-4.0.0.jar --platform windows64 --jdk C:\Users\[User]\.jdks\corretto-17.0.12.tar --useZgcIfSupportedOs --executable vincr --classpath lwjgl3\build\libs\Vincr-0.1.0.jar --mainclass git.vincr.lwjgl3.Lwjgl3Launcher --resources assets --output out-windows
