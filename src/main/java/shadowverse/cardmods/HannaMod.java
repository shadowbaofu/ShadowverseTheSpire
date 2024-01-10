package shadowverse.cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import shadowverse.characters.AbstractShadowversePlayer;

public class HannaMod extends AbstractCardModifier {
    public static String ID = "shadowverse:HannaMod";

    public void onInitialApplication(AbstractCard card) {
        card.rawDescription = CardCrawlGame.languagePack.getUIString("shadowverse:HannaMod").TEXT[0] + card.rawDescription;
        card.rawDescription = appendDescription(card.rawDescription,card);
    }

    public String appendDescription(String rawDescription,AbstractCard card){
        StringBuilder sb = new StringBuilder();
        for (String word : rawDescription.split(" ")) {
            word = word.trim();
            if (word.equals("!Hanna:D!")) {
                word = String.valueOf(applyPowers(card));
            }
            sb.append(word);
            sb.append(" ");
        }
        return sb.toString();
    }

    @Override
    public void onUpdate(AbstractCard card) {
        super.onUpdate(card);
        card.rawDescription = appendDescription(card.rawDescription,card);
    }



    public int applyPowers(AbstractCard card) {
        AbstractPlayer player = AbstractDungeon.player;
        int damage = 3;
        float tmp = 3;
        for (AbstractRelic r : player.relics) {
            tmp = r.atDamageModify(tmp, card);
            for (AbstractPower p : player.powers)
                tmp = p.atDamageGive(tmp, DamageInfo.DamageType.NORMAL);
            tmp = player.stance.atDamageGive(tmp, DamageInfo.DamageType.NORMAL);
            for (AbstractPower p : player.powers)
                tmp = p.atDamageFinalGive(tmp, DamageInfo.DamageType.NORMAL);
            if (tmp < 0.0F)
                tmp = 0.0F;
            damage = MathUtils.floor(tmp);
        }
        return damage;
    }

    public boolean shouldApply(AbstractCard card) {
        return true;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        AbstractDungeon.actionManager.addToBottom((new DamageAllEnemiesAction(AbstractDungeon.player, 3, DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.SLASH_DIAGONAL)));
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new HannaMod();
    }

    public String identifier(AbstractCard card) {
        return ID;
    }
}
