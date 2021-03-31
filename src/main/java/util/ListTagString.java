package util;

import nbt.tag.CompoundTag;
import nbt.tag.EndTag;
import nbt.tag.ListTag;
import nbt.tag.Tag;

public class ListTagString {
    private ListTag listTag;
    private String listTagString;

    public ListTagString(ListTag listTag) {
        this.listTag = listTag;
        this.listTagString = this.stringify();
    }

    public String getString() {
        return this.listTagString;
    }

    private String stringify() {
        int depth = 1;
        String whitespaces = this.getWhitespacesBasedOnDepth(depth);
        String finalString = this.listTag.toString() + this.getWhitespacesBasedOnDepth(depth - 1) + "{\n";

        for (Tag currentTag : this.listTag.getPayload()) {
            if (currentTag instanceof CompoundTag) {
                finalString += this.stringifyCompoundTag((CompoundTag) currentTag, depth + 1);
            } else if (currentTag instanceof EndTag) {
                finalString += this.getWhitespacesBasedOnDepth(depth - 1) + "}\n";
            } else if (currentTag instanceof ListTag) {
                finalString += this.stringifyListTag((ListTag) currentTag, depth + 1);
            } else {
                finalString += whitespaces + currentTag.toString();
            }
        }

        return finalString;
    }

    private String stringifyCompoundTag(CompoundTag tag, int depth) {
        String whitespaces = this.getWhitespacesBasedOnDepth(depth-1);
        String finalString = whitespaces + tag.toString() + whitespaces + "{\n";

        for (Tag currentTag : tag.getPayload()) {
            if (currentTag instanceof EndTag) {
                finalString += this.getWhitespacesBasedOnDepth(depth-1) + "}\n";
            } else if (currentTag instanceof CompoundTag) {
                finalString += this.stringifyCompoundTag((CompoundTag) currentTag, depth + 1);
            } else if (currentTag instanceof ListTag) {
                finalString += this.stringifyListTag((ListTag) currentTag, depth + 1);
            } else {
                finalString += this.getWhitespacesBasedOnDepth(depth) + currentTag.toString();
            }
        }

        return finalString;
    }

    private String stringifyListTag(ListTag<?> listTag, int depth) {
        String whitespaces = this.getWhitespacesBasedOnDepth(depth-1);
        String finalString = whitespaces + listTag.toString() + whitespaces + "{\n";

        for (Tag currentTag : listTag.getPayload()) {
            if (currentTag instanceof CompoundTag) {
                finalString += this.stringifyCompoundTag((CompoundTag) currentTag, depth + 1);
            } else if (currentTag instanceof ListTag) {
                finalString += this.stringifyListTag((ListTag) currentTag, depth + 1);
            } else {
                finalString += this.getWhitespacesBasedOnDepth(depth) + currentTag.toString();
            }
        }

        return finalString + this.getWhitespacesBasedOnDepth(depth-1) + "}\n";
    }

    private String getWhitespacesBasedOnDepth(int depth) {
        String whitespaces = "";
        for (int i = 0; i < depth * 4; i++) {
            whitespaces += " ";
        }

        return whitespaces;
    }
}