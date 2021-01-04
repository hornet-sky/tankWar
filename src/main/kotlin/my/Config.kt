package my

// 单例
object Config {
    const val block:Int = 64 // 区块尺寸
    const val gameWindowWidth = block * 13 // 窗体宽度
    const val gameWindowHeight = block * 13 // 窗体高度
    const val defSpeed = 8   // 默认速度，应该是区块尺寸的约数
    const val defAttackPower = 1   // 默认攻击力
    const val defBlood = 1   // 默认血量
}