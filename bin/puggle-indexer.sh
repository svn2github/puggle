<<<<<<< .mine
#!/bin/bash
java -Dswing.defaultlaf=com.sun.java.swing.plaf.windows.WindowsLookAndFeel -Xms2M -Xmx512M -cp ..\dist\Puggle.jar puggle.ui.IndexerFrame
=======
#!/bin/bash

export JAVA_BIN=java

$JAVA_BIN -Dswing.defaultlaf=com.sun.java.swing.plaf.windows.WindowsLookAndFeel -Xms2M -Xmx512M -cp ../dist/Puggle.jar puggle.ui.IndexerFrame  $*

>>>>>>> .r66
