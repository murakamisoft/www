Attribute VB_Name = "API"
Option Explicit

Public Declare Function BitBlt Lib "gdi32" (ByVal hDestDC As Long, ByVal x As Long, ByVal y As Long, ByVal nWidth As Long, ByVal nHeight As Long, ByVal hSrcDC As Long, ByVal xSrc As Long, ByVal ySrc As Long, ByVal dwRop As Long) As Long
Public Declare Function StretchBlt Lib "gdi32" (ByVal hdc As Long, ByVal x As Long, ByVal y As Long, ByVal nWidth As Long, ByVal nHeight As Long, ByVal hSrcDC As Long, ByVal xSrc As Long, ByVal ySrc As Long, ByVal nSrcWidth As Long, ByVal nSrcHeight As Long, ByVal dwRop As Long) As Long
Public Declare Function mciSendString Lib "winmm.dll" Alias "mciSendStringA" (ByVal lpstrCommand As String, ByVal lpstrReturnString As String, ByVal uReturnLength As Long, ByVal hwndCallback As Long) As Long
'    Ret = mciSendString("close all", vbNullString, 0, 0)
'    Ret = mciSendString("open " & "sample.mid" & " alias MIDI", vbNullString, 0, 0)
'    Ret = mciSendString("play MIDI notify", vbNullString, 0, 0)

Public Declare Function sndPlaySound Lib "winmm.dll" Alias "sndPlaySoundA" (ByVal lpszSoundName As String, ByVal uFlags As Long) As Long
Public Declare Function timeGetTime Lib "winmm.dll" () As Long
Public Declare Function SetPixelV Lib "gdi32" (ByVal hdc As Long, ByVal x As Long, ByVal y As Long, ByVal crColor As Long) As Long
Public Declare Function GetPixel Lib "gdi32" (ByVal hdc As Long, ByVal x As Long, ByVal y As Long) As Long
Public Declare Function GetAsyncKeyState Lib "user32" (ByVal vKey As Long) As Integer
Public Declare Function ShellExecute Lib "shell32.dll" Alias "ShellExecuteA" (ByVal hwnd As Long, ByVal lpOperation As String, ByVal lpFile As String, ByVal lpParameters As String, ByVal lpDirectory As String, ByVal nShowCmd As Long) As Long
    'Call ShellExecute(Me.hwnd, "open", "http://www.yahoo.co.jp", vbNullString, vbNullString, 3)
Public Declare Function WinHelp Lib "user32" Alias "WinHelpA" (ByVal hwnd As Long, ByVal lpHelpFile As String, ByVal wCommand As Long, ByVal dwData As Long) As Long
    'App.HelpFile = "filename.hlp"
    'ret = WinHelp(frmMain.hwnd, App.HelpFile, HELP_FINDER, ByVal 0&)
Public Const HELP_FINDER = &HB&

Public Const SRCAND = &H8800C6  ' (DWORD) dest = source AND dest
Public Const SRCCOPY = &HCC0020 ' (DWORD) dest = source
Public Const SRCERASE = &H440328        ' (DWORD) dest = source AND (NOT dest )
Public Const SRCINVERT = &H660046       ' (DWORD) dest = source XOR dest
Public Const SRCPAINT = &HEE0086        ' (DWORD) dest = source OR dest
Public Const SND_ASYNC = &H1         '  play asynchronously
Public Const SND_NODEFAULT = &H2         '  silence not default, if sound not found
Function RndNo(low As Integer, high As Integer) As Integer
'-------------------------------------------------------------------------------------
' 関数名 : RndNo
' 用途　 : 乱数の発生処理
' 引数　 : x = 乱数の最低値
' 　　　   y = 乱数の最大値
' 返り値 : 乱数
' 備考　 : なし
'-------------------------------------------------------------------------------------
    RndNo = low + Int(Rnd * (high - low + 1))

End Function
Function Atari(x1, y1, wid1 As Integer, hei1 As Integer, x2, y2, wid2 As Integer, hei2 As Integer) As Boolean

    'あたり判定用
    If x1 + wid1 > x2 And x1 < x2 + wid2 And y1 + hei1 > y2 And y1 < y2 + hei2 Then
        Atari = True 'あたっとります！
    End If
End Function

Sub Wait(WaitTime As Long) 'ウェイトをかける
Dim wT1 As Long
Dim wT2 As Long
    wT1 = timeGetTime
    Do
    DoEvents
        wT2 = timeGetTime
        If wT2 - wT1 >= WaitTime Then
            Exit Do
        End If
    Loop
End Sub
Sub Sound(SoundName As String) '-----効果音の再生-----
    sndPlaySound App.Path & "\" & SoundName & ".wav", SND_ASYNC Or SND_NODEFAULT
End Sub
Sub Music(MusicName As String) 'ミュージックの再生
    ret = mciSendString("close all", vbNullString, 0, 0)
    ret = mciSendString("open " & "data\" & MusicName & ".mid" & " alias MIDI", vbNullString, 0, 0)
    ret = mciSendString("play MIDI notify", vbNullString, 0, 0)
End Sub
