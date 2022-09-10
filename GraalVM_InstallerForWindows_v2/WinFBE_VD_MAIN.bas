#Define UNICODE
#Define _WIN32_WINNT &h0602
#Include Once "windows.bi"
#Include Once "Afx\CWindow.inc"
#define CODEGEN_FORM
#define CODEGEN_PICTUREBOX
#define CODEGEN_FRAME
#define CODEGEN_LABEL
#define CODEGEN_COMBOBOX
#define CODEGEN_TEXTBOX
#define CODEGEN_BUTTON
#Include once "WinFormsX\WinFormsX.bi"
Using Afx

' WINFBE_CODEGEN_START

Declare Function frmMain_ButtonInstall_Click( ByRef sender As wfxButton, ByRef e As EventArgs ) As LRESULT
Declare Function frmMain_ButtonExit_Click( ByRef sender As wfxButton, ByRef e As EventArgs ) As LRESULT

type frmMainType extends wfxForm
   private:
      temp as byte
   public:
      declare static function FormInitializeComponent( byval pForm as frmMainType ptr ) as LRESULT
      declare constructor
      ' Controls
      Picture1 As wfxPictureBox
      Frame1 As wfxFrame
      Label1 As wfxLabel
      Combo1 As wfxComboBox
      Label2 As wfxLabel
      Label3 As wfxLabel
      Text1 As wfxTextBox
      Button1 As wfxButton
      Label4 As wfxLabel
      ButtonInstall As wfxButton
      ButtonExit As wfxButton
end type


function frmMainType.FormInitializeComponent( byval pForm as frmMainType ptr ) as LRESULT
   dim as long nClientOffset

   pForm->Name = "frmMain"
   pForm->Text = "GraalVM Installer for Windows v2.0.0"
   pForm->StartPosition = FormStartPosition.CenterScreen
   pForm->MinimizeBox = False
   pForm->MaximizeBox = False
   pForm->SetBounds(10,10,548,418)
   pForm->Picture1.Parent = pForm
   pForm->Picture1.Name = "Picture1"
   pForm->Picture1.Text = "Picture1"
   pForm->Picture1.BackColor = Colors.SystemControl
   pForm->Picture1.BackColorHot = Colors.SystemControl
   pForm->Picture1.Image = "IMAGE_MAINLOGO"
   pForm->Picture1.SetBounds(62,12-nClientOffset,396,123)
   pForm->Frame1.Parent = pForm
   pForm->Frame1.Name = "Frame1"
   pForm->Frame1.Text = "Installation Properties"
   pForm->Frame1.SetBounds(16,149-nClientOffset,500,158)
   pForm->Label1.Parent = pForm
   pForm->Label1.Name = "Label1"
   pForm->Label1.Text = "GraalVM Version:"
   pForm->Label1.TextAlign = LabelAlignment.MiddleLeft
   pForm->Label1.Font = New wfxFont("Segoe UI",10,FontStyles.Normal,FontCharset.Ansi)
   pForm->Label1.SetBounds(31,176-nClientOffset,116,19)
   pForm->Combo1.Parent = pForm
   pForm->Combo1.Name = "Combo1"
   pForm->Combo1.Font = New wfxFont("Segoe UI",10,FontStyles.Normal,FontCharset.Ansi)
   pForm->Combo1.Text = "Combo1"
   pForm->Combo1.SetBounds(156,176-nClientOffset,340,25)
   pForm->Label2.Parent = pForm
   pForm->Label2.Name = "Label2"
   pForm->Label2.Text = "Select the GraalVM Version you want to install on your computer"
   pForm->Label2.TextAlign = LabelAlignment.MiddleLeft
   pForm->Label2.Font = New wfxFont("Segoe UI",8,FontStyles.Normal,FontCharset.Ansi)
   pForm->Label2.SetBounds(31,208-nClientOffset,438,19)
   pForm->Label3.Parent = pForm
   pForm->Label3.Name = "Label3"
   pForm->Label3.Text = "Installation Path:"
   pForm->Label3.TextAlign = LabelAlignment.MiddleLeft
   pForm->Label3.Font = New wfxFont("Segoe UI",10,FontStyles.Normal,FontCharset.Ansi)
   pForm->Label3.SetBounds(31,240-nClientOffset,102,19)
   pForm->Text1.Parent = pForm
   pForm->Text1.Name = "Text1"
   pForm->Text1.Font = New wfxFont("Segoe UI",10,FontStyles.Normal,FontCharset.Ansi)
   pForm->Text1.Text = "C:\GraalVM"
   pForm->Text1.SetBounds(157,240-nClientOffset,251,24)
   pForm->Button1.Parent = pForm
   pForm->Button1.Name = "Button1"
   pForm->Button1.Text = "..."
   pForm->Button1.SetBounds(421,240-nClientOffset,73,25)
   pForm->Label4.Parent = pForm
   pForm->Label4.Name = "Label4"
   pForm->Label4.Text = "We suggest you install GraalVM to " & chr(34) & "C:\GraalVM" & chr(34) & " path"
   pForm->Label4.TextAlign = LabelAlignment.MiddleLeft
   pForm->Label4.Font = New wfxFont("Segoe UI",8,FontStyles.Normal,FontCharset.Ansi)
   pForm->Label4.SetBounds(31,271-nClientOffset,438,19)
   pForm->ButtonInstall.Parent = pForm
   pForm->ButtonInstall.Name = "ButtonInstall"
   pForm->ButtonInstall.Text = "Install"
   pForm->ButtonInstall.SetBounds(16,317-nClientOffset,107,29)
   pForm->ButtonInstall.OnClick = @frmMain_ButtonInstall_Click
   pForm->ButtonExit.Parent = pForm
   pForm->ButtonExit.Name = "ButtonExit"
   pForm->ButtonExit.Text = "Exit"
   pForm->ButtonExit.SetBounds(136,317-nClientOffset,107,29)
   pForm->ButtonExit.OnClick = @frmMain_ButtonExit_Click
   pForm->Controls.Add(ControlType.ComboBox, @(pForm->Combo1))
   pForm->Controls.Add(ControlType.TextBox, @(pForm->Text1))
   pForm->Controls.Add(ControlType.Button, @(pForm->Button1))
   pForm->Controls.Add(ControlType.Button, @(pForm->ButtonInstall))
   pForm->Controls.Add(ControlType.Button, @(pForm->ButtonExit))
   pForm->Controls.Add(ControlType.PictureBox, @(pForm->Picture1))
   pForm->Controls.Add(ControlType.Frame, @(pForm->Frame1))
   pForm->Controls.Add(ControlType.Label, @(pForm->Label1))
   pForm->Controls.Add(ControlType.Label, @(pForm->Label2))
   pForm->Controls.Add(ControlType.Label, @(pForm->Label3))
   pForm->Controls.Add(ControlType.Label, @(pForm->Label4))
   Application.Forms.Add(ControlType.Form, pForm)
   function = 0
end function

constructor frmMainType
   InitializeComponent = cast( any ptr, @FormInitializeComponent )
   this.FormInitializeComponent( @this )
end constructor

dim shared frmMain as frmMainType

' WINFBE_CODEGEN_END


#include once "C:\Development\GraalVM-Windows-Installer\GraalVM_InstallerForWindows_v2\frmMain.inc"

