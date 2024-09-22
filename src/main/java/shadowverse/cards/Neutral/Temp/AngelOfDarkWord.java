package shadowverse.cards.Neutral.Temp;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.DaggerSprayEffect;
import shadowverse.cards.Neutral.Neutral.AngelOfTheWord;
import shadowverse.characters.AbstractShadowversePlayer;

public class AngelOfDarkWord extends CustomCard {
    public static final String ID = "shadowverse:AngelOfDarkWord";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/AngelOfDarkWord.png";


    public AngelOfDarkWord() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.ALL_ENEMY);
        this.baseDamage = 11;
        this.isMultiDamage = true;
        this.tags.add(AbstractShadowversePlayer.Enums.DARK_ANGEL);
        this.cardsToPreview = new AngelOfTheWord();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.upgradeDamage(3);
            this.cardsToPreview.upgrade();
        }
    }


    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("AngelOfTheWord"));
        addToBot(new VFXAction(new DaggerSprayEffect(false), 0.0F));
        addToBot(new DamageAllEnemiesAction(abstractPlayer, this.damage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }


    @Override
    public AbstractCard makeCopy() {
        return new AngelOfDarkWord();
    }
}


