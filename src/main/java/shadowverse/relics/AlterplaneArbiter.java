package shadowverse.relics;

import basemod.abstracts.CustomRelic;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.powers.BufferPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import shadowverse.characters.*;
import shadowverse.reward.ClassReward;

import java.util.ArrayList;

public class AlterplaneArbiter extends CustomRelic {
    public static final String ID = "shadowverse:AlterplaneArbiter";
    public static final String IMG = "img/relics/AlterplaneArbiter.png";
    public static final String OUTLINE_IMG = "img/relics/outline/AlterplaneArbiter_Outline.png";

    public static AbstractCard.CardColor[] COLORS = {
            Witchcraft.Enums.COLOR_BLUE,
            Elf.Enums.COLOR_GREEN,
            Necromancer.Enums.COLOR_PURPLE,
            Vampire.Enums.COLOR_SCARLET,
            Nemesis.Enums.COLOR_SKY,
            Royal.Enums.COLOR_YELLOW,
            Bishop.Enums.COLOR_WHITE,
            Dragon.Enums.COLOR_BROWN
    };
    public static AbstractPlayer.PlayerClass[] CLASSES = {
            Witchcraft.Enums.WITCHCRAFT,
            Elf.Enums.Elf,
            Necromancer.Enums.Necromancer,
            Vampire.Enums.Vampire,
            Nemesis.Enums.Nemesis,
            Royal.Enums.Royal,
            Bishop.Enums.Bishop,
            Dragon.Enums.Dragon
    };

    public AlterplaneArbiter() {
        super(ID, ImageMaster.loadImage(IMG), RelicTier.SPECIAL, LandingSound.SOLID);
    }

    @Override
    public String getUpdatedDescription() {
        String s;
        if (this.counter < 0) {
            s = this.DESCRIPTIONS[0];
        } else {
            s = this.DESCRIPTIONS[1];
            if (COLORS[this.counter] == Witchcraft.Enums.COLOR_BLUE) {
                s += this.DESCRIPTIONS[3] + this.DESCRIPTIONS[2];
            } else if (COLORS[this.counter] == Elf.Enums.COLOR_GREEN) {
                s += this.DESCRIPTIONS[4] + this.DESCRIPTIONS[2];
            } else if (COLORS[this.counter] == Necromancer.Enums.COLOR_PURPLE) {
                s += this.DESCRIPTIONS[5] + this.DESCRIPTIONS[2];
            } else if (COLORS[this.counter] == Vampire.Enums.COLOR_SCARLET) {
                s += this.DESCRIPTIONS[6] + this.DESCRIPTIONS[2];
            } else if (COLORS[this.counter] == Nemesis.Enums.COLOR_SKY) {
                s += this.DESCRIPTIONS[7] + this.DESCRIPTIONS[2];
            } else if (COLORS[this.counter] == Royal.Enums.COLOR_YELLOW) {
                s += this.DESCRIPTIONS[8] + this.DESCRIPTIONS[2];
            } else if (COLORS[this.counter] == Bishop.Enums.COLOR_WHITE) {
                s += this.DESCRIPTIONS[9] + this.DESCRIPTIONS[2];
            }else if (COLORS[this.counter] == Dragon.Enums.COLOR_BROWN){
                s += this.DESCRIPTIONS[10] + this.DESCRIPTIONS[2];
            }
        }
        return s;
    }

    @Override
    public void onEnterRoom(AbstractRoom room) {
        flash();
        AbstractPlayer p = AbstractDungeon.player;
        if (p.maxOrbs < 5) {
            int toIncrease = 5 - p.maxOrbs;
            addToBot(new IncreaseMaxOrbAction(toIncrease));
        }
        for (PowerTip t : this.tips) {
            if (t.header.equals(this.name)) {
                t.body = this.getUpdatedDescription();
            }
        }
    }

    @Override
    public void onVictory() {
        AbstractRoom room = AbstractDungeon.getCurrRoom();
        if (room instanceof MonsterRoom && !(room instanceof MonsterRoomElite) && !(room instanceof MonsterRoomBoss) && !AbstractDungeon.getMonsters().haveMonstersEscaped()) {
            AbstractDungeon.getCurrRoom().addCardReward(new ClassReward(COLORS[this.counter]));
        }
    }

    @Override
    public void onEquip() {
        AbstractPlayer p = AbstractDungeon.player;
        while (true) {
            this.counter = AbstractDungeon.miscRng.random(6);
            if (CLASSES[this.counter] != p.chosenClass) {
                break;
            }
        }
        if (CLASSES[this.counter] == Royal.Enums.Royal || CLASSES[this.counter] == Bishop.Enums.Bishop) {
            AbstractDungeon.player.masterMaxOrbs = 5;
        }
        for (PowerTip t : this.tips) {
            if (t.header.equals(this.name)) {
                t.body = this.getUpdatedDescription();
            }
        }
    }


    @Override
    public AbstractRelic makeCopy() {
        return new AlterplaneArbiter();
    }
}




