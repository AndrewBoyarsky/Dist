import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinBase;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.IntByReference;

public class JavaNamedPipe {
    public static void main(String[] args) {
        String    pipeName="\\\\.\\pipe\\" + "namedPipe";
        WinNT.HANDLE hNamedPipe=Kernel32.INSTANCE.CreateNamedPipe(pipeName,
                WinBase.PIPE_ACCESS_DUPLEX,        // dwOpenMode
                WinBase.PIPE_TYPE_BYTE | WinBase.PIPE_READMODE_BYTE | WinBase.PIPE_WAIT,    // dwPipeMode
                1,    // nMaxInstances,
                Byte.MAX_VALUE,    // nOutBufferSize,
                Byte.MAX_VALUE,    // nInBufferSize,
                1000,    // nDefaultTimeOut,
                null);    // lpSecurityAttributes
        assertCallSucceeded("CreateNamedPipe", !WinBase.INVALID_HANDLE_VALUE.equals(hNamedPipe));

        // NOTE: we don't really care what the returned values are only that the call succeeds
        try {
            IntByReference lpFlags = new IntByReference(0);
            IntByReference lpOutBuffferSize = new IntByReference(0);
            IntByReference lpInBufferSize = new IntByReference(0);
            IntByReference lpMaxInstances = new IntByReference(0);
            assertCallSucceeded("GetNamedPipeInfo",
                    Kernel32.INSTANCE.GetNamedPipeInfo(hNamedPipe, lpFlags,
                            lpOutBuffferSize, lpInBufferSize, lpMaxInstances));
            while (true) {
                Kernel32.INSTANCE.WriteFile()
            }
            WinDef.ULONGByReference ServerProcessId = new WinDef.ULONGByReference();
            assertCallSucceeded("GetNamedPipeServerProcessId", Kernel32.INSTANCE.GetNamedPipeServerProcessId(hNamedPipe, ServerProcessId));

            WinDef.ULONGByReference ServerSessionId = new WinDef.ULONGByReference();
            assertCallSucceeded("GetNamedPipeServerSessionId", Kernel32.INSTANCE.GetNamedPipeServerSessionId(hNamedPipe, ServerSessionId));

            assertCallSucceeded("DisconnectNamedPipe", Kernel32.INSTANCE.DisconnectNamedPipe(hNamedPipe));
        } finally {    // clean up
            assertCallSucceeded("Named pipe handle close", Kernel32.INSTANCE.CloseHandle(hNamedPipe));
        }
    }

    private static void assertCallSucceeded(String message, boolean success) {
        if (!success) {
            throw new RuntimeException(message + " is invalid");
        }
    }
}
