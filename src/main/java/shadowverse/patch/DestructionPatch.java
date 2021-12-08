package shadowverse.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import shadowverse.cards.Temp.BlackArtifact;
import shadowverse.cards.Temp.WhiteArtifact;

public class DestructionPatch {
    @SpirePatch(clz = CardGroup.class, method = "moveToExhaustPile")
    public static class Destruction {
        @SpirePrefixPatch
        public static SpireReturn Post(CardGroup group, AbstractCard c) {
            if (c instanceof WhiteArtifact||c instanceof BlackArtifact)
                return SpireReturn.Return(null);
            return SpireReturn.Continue();
        }
    }
}
