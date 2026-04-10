class Note {
  final String? id;
  final String? title;
  final dynamic content;

  Note({
    this.id,
    this.title,
    this.content,
  });

  factory Note.fromJson(Map<String, dynamic> json) => Note(
      id: json["noteId"], title: json["noteTopic"], content: json["noteBody"]);

  Map<String, dynamic> toJson(Map<String, dynamic> data) {
    data['noteId'] = id;
    data['noteTopic'] = title;
    data['noteBody'] = content;

    return data;
  }
}
