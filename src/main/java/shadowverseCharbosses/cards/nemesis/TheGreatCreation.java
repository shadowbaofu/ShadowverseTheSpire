package shadowverseCharbosses.cards.nemesis;

import shadowverseCharbosses.actions.common.EnemyMakeTempCardInHandAction;
import shadowverseCharbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import shadowverse.characters.Nemesis;

import java.util.ArrayList;

public class TheGreatCreation extends AbstractBossCard {
    public static final String ID = "shadowverse:TheGreatCreation";

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:TheGreatCreation");

    public static final String IMG_PATH = "img/cards/TheGreatCreation.png";

    public TheGreatCreation() {
        super(ID, cardStrings.NAME, IMG_PATH, 2, cardStrings.DESCRIPTION, CardType.SKILL, Nemesis.Enums.COLOR_SKY, CardRarity.RARE, CardTarget.SELF, AbstractMonster.Intent.ATTACK_BUFF);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.cardsToPreview = new EnAncientArtifact();
        this.baseDamage = 15;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot((AbstractGameAction)new VFXAction((AbstractCreature)m, (AbstractGameEffect)new InflameEffect((AbstractCreature)m), 1.0F));
        addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)m, (AbstractCreature)m, (AbstractPower)new StrengthPower((AbstractCreature)m, this.magicNumber), this.magicNumber));
        addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)m, (AbstractCreature)m, (AbstractPower)new DexterityPower((AbstractCreature)m, this.magicNumber), this.magicNumber));
        addToBot((AbstractGameAction)new EnemyMakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy()));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }

    public int getPriority(ArrayList<AbstractCard> hand) {
        return 100;
    }

    public AbstractCard makeCopy() {
        return (AbstractCard)new TheGreatCreation();
    }
}
