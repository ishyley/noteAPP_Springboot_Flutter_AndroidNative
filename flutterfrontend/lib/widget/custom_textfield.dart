import 'package:flutter/material.dart';

// ignore: must_be_immutable
class CustomTextfield extends StatelessWidget {
  TextEditingController controller = TextEditingController();
  final bool? showCursor;
  final String? hintText;
  final int? maxLines;
  final double? cursorHeight;
  final double? fontSize;
  final FontWeight? fontWeight;
  final double? hintfontSize;
  final FontWeight? hintfontWeight;
  CustomTextfield({
    super.key,
    required this.controller,
    this.showCursor,
    this.hintText,
    this.maxLines,
    this.cursorHeight,
    this.fontSize,
    this.fontWeight,
    this.hintfontSize,
    this.hintfontWeight,
  });

  @override
  Widget build(BuildContext context) {
    return TextField(
      controller: controller,
      showCursor: showCursor,

      scrollController: ScrollController(),
      cursorHeight: cursorHeight,
      cursorColor: Colors.black,
      style: TextStyle(
          color: Colors.black,
          fontSize: fontSize,
          fontWeight: fontWeight), //might not be working
      decoration: InputDecoration(
          hintText: hintText,
          hintStyle: TextStyle(
              color: Colors.white,
              fontSize: hintfontSize,
              fontWeight: hintfontWeight),
          border: InputBorder.none),
      maxLines: maxLines,
    );
  }
}
