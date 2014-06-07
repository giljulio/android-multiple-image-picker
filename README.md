#Photo picker - multiple

Recently I got frustrated opening an intent to select multiple photos and also with a camera. Therefore I to make it myself and so other people dont have to reinvent the wheel here it is.



##Usage

private static final RESULT_CODE_PICKER_IMAGES = 2674;

Intent intent = new Intent(this, SmartImagePicker.class);
startActivityForResult(intent, RESULT_CODE_PICKER_IMAGES);

