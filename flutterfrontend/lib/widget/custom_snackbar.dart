import 'package:flutter/material.dart';

showSnackbar(
  BuildContext context,
  String text, {
  Color backgroundColor = Colors.black,
  IconData icon = Icons.info,
  Duration duration = const Duration(seconds: 5),
  String? action,
  VoidCallback? onActionPressed,
  SnackBarBehavior? behavior = SnackBarBehavior.floating,
  EdgeInsetsGeometry? margin,
}) {
  ScaffoldMessenger.of(context).removeCurrentSnackBar();
  ScaffoldMessenger.of(context).showSnackBar(
    SnackBar(
      content: Row(
        children: [
          Icon(icon, color: Colors.white),
          SizedBox(width: 10),
          Expanded(
            child: Text(
              text,
              style: TextStyle(color: Colors.white),
            ),
          ),
        ],
      ),

      backgroundColor: backgroundColor,
      behavior: behavior, // Makes it float above the UI
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(10),
      ),
      margin: margin,
      duration: duration,
      action: action != null
          ? SnackBarAction(
              label: action,
              textColor: Colors.yellow, // Customize action text color
              onPressed: onActionPressed ?? () {}, // Default to empty function
            )
          : null,
    ),
  );
}
