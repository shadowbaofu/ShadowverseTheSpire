package shadowverse.cards.Nemesis.Artifact;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.VerticalImpactEffect;
import shadowverse.action.DrawPileToHandAction_Tag;
import shadowverse.cards.Witch.AbstractAccelerateCard;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Nemesis;

import java.util.ArrayList;


public class CelestialArtifact extends AbstractAccelerateCard {
    public static final String ID = "shadowverse:CelestialArtifact";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:CelestialArtifact");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/CelestialArtifact.png";

    public CelestialArtifact() {
        super(ID, NAME, IMG_PATH, 3, DESCRIPTION, CardType.ATTACK, Nemesis.Enums.COLOR_SKY, CardRarity.UNCOMMON, CardTarget.ENEMY, 0, CardType.SKILL);
        this.tags.add(AbstractShadowversePlayer.Enums.ARTIFACT);
        this.baseMagicNumber = 5;
        this.magicNumber = this.baseMagicNumber;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(3);
        }
    }

    @Override
    public void baseUse(AbstractPlayer p, AbstractMonster m) {
        int count = 0;
        ArrayList<String> dup = new ArrayList<>();
        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
            if (c.hasTag(AbstractShadowversePlayer.Enums.ARTIFACT) && !dup.contains(c.cardID)) {
                dup.add(c.cardID);
                count++;
            }
        }
        if (m != null)
            addToBot(new VFXAction(new VerticalImpactEffect(m.hb.cX + m.hb.width / 4.0F, m.hb.cY - m.hb.height / 4.0F)));
        addToBot(new DamageAction(m, new DamageInfo(p, this.magicNumber * count, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    @Override
    public void accUse(AbstractPlayer p, AbstractMonster m) {
        int count = 0;
        ArrayList<String> dup = new ArrayList<>();
        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
            if (c.hasTag(AbstractShadowversePlayer.Enums.ARTIFACT) && !dup.contains(c.cardID)) {
                dup.add(c.cardID);
                count++;
            }
        }
        addToBot(new DrawPileToHandAction_Tag(1, AbstractShadowversePlayer.Enums.ARTIFACT, null));
        if (count >= 6)
            addToBot(new DrawPileToHandAction_Tag(1, AbstractShadowversePlayer.Enums.ARTIFACT, null));
    }


    public AbstractCard makeCopy() {
        return new CelestialArtifact();
    }
}
