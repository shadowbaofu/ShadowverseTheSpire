package shadowverse.cards.Neutral.Temp;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import shadowverse.characters.Elf;

public class DelugeOfArrows extends CustomCard {
    public static final String ID = "shadowverse:DelugeOfArrows";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:DelugeOfArrows");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/DelugeOfArrows.png";

    public DelugeOfArrows() {
        super(ID, NAME, IMG_PATH, 3, DESCRIPTION, CardType.SKILL, Elf.Enums.COLOR_GREEN, CardRarity.SPECIAL, CardTarget.ALL_ENEMY);
        this.baseDamage = 4;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeDamage(2);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        int amount = 0;
        if (p.hand.group.size() < 7) {
            addToBot(new DrawCardAction(8 - p.hand.group.size(), new AbstractGameAction() {
                @Override
                public void update() {
                    amount = DrawCardAction.drawnCards.size();
                    addToBot(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(DelugeOfArrows.this.damage * amount, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE, true));
                    this.isDone = true;
                }
            }));
        }
        addToBot(new VFXAction(p, new CleaveEffect(), 0.1F));
    }

    @Override
    public AbstractCard makeCopy() {
        return new DelugeOfArrows();
    }
}