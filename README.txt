/*********************************************************************/
 *
 *    Y O C T O P U C E    L I B R A R Y    f o r    J a v a
 *
 * - - - - - - - - - License information: - - - - - - - - - - - - - -
 *
 *  Copyright (C) 2011 and beyond by Yoctopuce Sarl, Switzerland.
 *
 *  Yoctopuce Sarl (hereafter Licensor) grants to you a perpetual
 *  non-exclusive license to use, modify, copy and integrate this
 *  file into your software for the sole purpose of interfacing
 *  with Yoctopuce products.
 *
 *  You may reproduce and distribute copies of this file in
 *  source or object form, as long as the sole purpose of this
 *  code is to interface with Yoctopuce products. You must retain
 *  this notice in the distributed source file.
 *
 *  You should refer to Yoctopuce General Terms and Conditions
 *  for additional information regarding your rights and
 *  obligations.
 *
 *  THE SOFTWARE AND DOCUMENTATION ARE PROVIDED "AS IS" WITHOUT
 *  WARRANTY OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING
 *  WITHOUT LIMITATION, ANY WARRANTY OF MERCHANTABILITY, FITNESS
 *  FOR A PARTICULAR PURPOSE, TITLE AND NON-INFRINGEMENT. IN NO
 *  EVENT SHALL LICENSOR BE LIABLE FOR ANY INCIDENTAL, SPECIAL,
 *  INDIRECT OR CONSEQUENTIAL DAMAGES, LOST PROFITS OR LOST DATA,
 *  COST OF PROCUREMENT OF SUBSTITUTE GOODS, TECHNOLOGY OR
 *  SERVICES, ANY CLAIMS BY THIRD PARTIES (INCLUDING BUT NOT
 *  LIMITED TO ANY DEFENSE THEREOF), ANY CLAIMS FOR INDEMNITY OR
 *  CONTRIBUTION, OR OTHER SIMILAR COSTS, WHETHER ASSERTED ON THE
 *  BASIS OF CONTRACT, TORT (INCLUDING NEGLIGENCE), BREACH OF
 *  WARRANTY, OR OTHERWISE.
 *
 *********************************************************************/

Content of this package:
------------------------
build.bat               Automated build script for Windows
build.sh                Automated build script for UNIX platforms
pom.xml                 Maven build script to compile library and examples
FILES.txt               List of files contained in this archive
RELEASE.txt             Release notes
Documentation/          API Reference, in HTML and PDF format
Examples/               Directory with sample programs in Java
Sources/                Source code of the high-level library (in Java)
Binaries/               Precompiled far files

In order to use the Java examples, you will probably need to download
as well the VirtualHub software for your OS.

The archive is shipped with precompiled libraries. If you want to rebuild
them from source, or to compile the examples, use the following command:

on Windows: build
on UNIX:    ./build.sh

If you use Maven you can recompile everything with
	mvn package

or use the library uploaded to maven central repository
    groupId: com.yoctopuce.java
    artifactId: yoctolib

For more details, refer to the documentation specific to each product, which
includes sample code with explanations, and a programming reference manual.
In case of trouble, contact support@yoctopuce.com

Have fun !
